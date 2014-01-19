require 'active_record'
require 'forwardable'
require 'sqlite3'

# If set to true the script will purge all of the recorded database information
PURGE_TABLES = false

class Region < ActiveRecord::Base

end

class Area < ActiveRecord::Base

end

module Environment
        extend SingleForwardable

        def_delegators :application, *%i(initialize! define commit_changes).map(&:to_sym)

        def self.application
                @application ||= Application.new
        end

        def initialize!

                # Initialize the database for the environment
                initialize_db '../config_test.db'

                # Initialize the schema for the database
                initialize_schema
        end

        def initialize_db(path)
                ActiveRecord::Base.establish_connection(
                    :adapter   => 'sqlite3',
                    :database  => path
                )
        end

        def initialize_schema
                ActiveRecord::Schema.define do

                        unless Area.table_exists? or PURGE_TABLES
                                create_table :areas do |t|
                                        t.string :name, null: false
                                end
                        end

                        unless Region.table_exists? or PURGE_TABLES
                                create_table :regions do |t|
                                        t.integer :loc, null: false

                                        %i(obj_data, tile_data, area_data).each do |field|
                                                t.string field,  default: ''
                                        end
                                end
                        end
                end
        end

        def define(type, args)
                create(type, args)
        end

        def create(type, args)
                bounds = args[:bounds]
                plane  = bounds[:z]

                r_width  = bounds[:width]  / 64
                r_height = bounds[:height] / 64

                upper_x = bounds[:x] + bounds[:width]
                upper_y = bounds[:y] + bounds[:height]

                x_current = bounds[:x]
                y_current = bounds[:y]

                (0..r_width).each do

                        # Calculate the coordinates for the calculation
                        rx       = x_current >> 6
                        rx_local = x_current - (rx << 6)

                        # Calculate the width of the area within the region
                        width  = upper_x - x_current
                        width  = width  + rx_local > 64 ? 64 - rx_local : width

                        (0..r_height).each do

                                ry       = y_current >> 6
                                ry_local = y_current - (ry << 6)

                                height = upper_y - y_current
                                height = height + ry_local > 64 ? 64 - ry_local : height

                                hash  = hash18(rx, ry, plane)
                                array = chunks[hash] ||= []

                                array << [ args[:name], rx_local, ry_local, width, height ]

                                # Increment the current y position
                                y_current += height
                        end

                        # Increment the current x by the width of the last chunk
                        x_current += width

                        # Reset the current y
                        y_current = bounds[:y]
                end
        end

        def commit_changes
                @chunks.each_pair do |loc, values|

                        region = Region.find_or_create_by(loc: loc)

                        ids      = []
                        buffer   = []
                        template = ''

                        values.each do |entry|

                                # Extract the data from the entry
                                name, x, y, width, height = entry

                                # Find or create the area from its name
                                area = Area.find_or_create_by(name: name)

                                # Append the area id to the list of ids for the region
                                ids << area.id unless ids.include? area.id

                                # Encode the area into the buffer and update the template
                                buffer   += [ 3, hash12(x, y), width, height, 1 << ids.index(area.id) ]
                                template << 'CS>CCC'
                        end

                        # Encode all of the ids
                        ids.each_with_index do |value, index|
                                buffer   += [ 1, index, value ]
                                template << 'CCS>'
                        end

                        # Append the ending opcode
                        buffer   += [ 0 ]
                        template << 'C'

                        # Encode and set the area data for the region
                        region.area_data = Base64.encode64(buffer.pack(template)).gsub(/\n/, '')

                        # Save the region
                        region.save!
                end

        end

        def chunks
                @chunks ||= {}
        end

        def hash12(x, y)
                y << 6 | x
        end

        def hash18(x, y, z)
                z << 16 | y << 8 | x
        end

        class Application
                include Environment
        end
end