$:.unshift 'data/plugins'

require 'java'
require 'fixins'
require 'plugin'
require 'hook'

class TestPlugin < Plugin::Base
        def load
                bind :button, :id => 0, :action => 'test'
        end

        def test(plr)
                p '#4'
        end
end