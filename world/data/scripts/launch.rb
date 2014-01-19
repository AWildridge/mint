# Fix for files not loading correctly
$:.unshift './'

require 'environment'
require 'forwardable'

# Initialize the environment
Environment.initialize!

# Delegate the define method to the global namespace
include SingleForwardable
def_delegators Environment, :define, :commit_changes

define :area, :name => 'some_area', :bounds => { x: 5000, y: 5000, z: 0, width: 500, height: 500 }

# Commit the changes
commit_changes