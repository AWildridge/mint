module Hook
        class << self
                def create(name, args, callback)
                        Base.new(types[name], args, callback)
                end

                def define(name)
                        types[name] = Type.new(name)
                end

                def types
                        @types ||= {}
                end
        end

        class Type
                def initialize(name)
                        @class_name = "#{name.to_s.camelize}ActionHandler"
                        java_import "us.evelus.world.interact.#{@class_name}"

                        @generated_class = Class.new(Object.const_get(@class_name))
                        @name            = name

                        @generated_class.class_eval <<-RUBY, __FILE__, __LINE__
                                def initialize(args, callback)
                                        super(*args)
                                        @callback = callback
                                end

                                def handle(*args)
                                        @callback.call(*args)
                                end
                        RUBY
                end

                def create(args, callback)
                        @generated_class.new(args, callback)
                end
        end

        class Base
                def initialize(type, args, callback)
                        @handler = type.create(args, callback)
                        @type    = type
                end

                def bind(context); context.bind(@handler) end

                def unbind(context); context.unbind(@handler) end
        end

        # Define each of the hook types
        define(:button)
        define(:player)
        define(:object)
end