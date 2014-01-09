require 'java'

java_import 'us.evelus.world.plugin.IPlugin'

module Plugin
        extend IPlugin

        def self.included(base)
                base.extend(ClassMethods)
        end

        def bind(type, args)
                raise 'Must specify action in arguments!' unless args.has_key? :action

                method = args[:action].respond_to?(:to_sym) ? args.delete(:action).to_sym : args.delete(:action)

                hook = Hook.create(type, args.values, ->(*args) { self.send(method, *args) })
                hook.bind(@context)

                hooks << hook

                hook
        end

        def setPluginContext(context)
                @context = context
        end

        def bind!
                hooks.each { |hook| hook.bind(@context) }
        end

        def unbind!
                hooks.each { |hook| hook.unbind(@context) }
        end

        def authors
                my.authors
        end

        def dependencies
                my.dependencies
        end

        def notes
                my.notes
        end

        def name
                my.name.gsub(/plugin/i, '').underscore
        end

        def hooks
                @hooks ||= []
        end

        module ClassMethods
                def inherited(base)
                        $loader.register(base.new)
                end

                def info(args={})
                        %w(authors dependencies notes).each do |type|
                                class_eval <<-RUBY, __FILE__, __LINE__
                                        @#{type} = args[:#{type}]
                                RUBY
                        end
                end

                def authors
                        @authors ||= []
                end

                def dependencies
                        @dependencies ||= []
                end

                def notes
                        @notes ||= 'There are no notes on this plugin.'
                end
        end

        class Base
                include Plugin

                # Called when a plugin is requested to unload, unbinds all of the registered hooks.
                # @return nil
                def unload
                        unbind!
                        nil
                end
        end
end

