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

        # Implemented java method from IPlugin interface, sets the plugin context.
        def setPluginContext(context)
                @context = context
        end

        # Implemented java method from IPlugin interface, gets the authors of the plugin.
        def getAuthors
                my.authors
        end

        # Implemented java method from IPlugin interface, gets the dependencies of the plugin
        # and loads them in listed order if they havent been loaded already.
        def getDependencies
                my.dependencies
        end

        # Implemented java method from IPlugin interface, gets the description of the plugin.
        def getDescription
                my.description
        end

        # Implemented java method from IPlugin interface, gets the name of the plugin
        # from the name of the plugin class in an underscored format.
        def getName
                my.name.gsub(/plugin/i, '').underscore
        end

        #
        # Binds all of the hooks registered to the plugin.
        #
        # @return nil
        #
        def bind!
                hooks.each { |hook| hook.bind(@context) }
                nil
        end

        def unbind!
                hooks.each { |hook| hook.unbind(@context) }
                nil
        end

        def hooks
                @hooks ||= []
        end

        module ClassMethods
                def inherited(base)
                        $loader.register(base.new)
                end

                def info(args={})
                        %w(authors dependencies description).each do |type|
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

                def description
                        @description ||= 'There are no notes on this plugin.'
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

