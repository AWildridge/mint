class Object
        def my
                self.class
        end
end

class String
        def camelize
                split("_").each {|s| s.capitalize! }.join("")
        end

        def underscore!
                gsub!(/(.)([A-Z])/,'\1_\2')
                downcase!
        end

        def underscore
                dup.tap { |s| s.underscore! }
        end
end
