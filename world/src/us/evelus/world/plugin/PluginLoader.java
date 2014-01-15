package us.evelus.world.plugin;

import org.jruby.internal.runtime.GlobalVariable.Scope;
import org.apache.commons.io.IOUtils;
import org.jruby.Ruby;
import org.jruby.javasupport.Java;
import org.jruby.runtime.GlobalVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public final class PluginLoader {

    private static final Logger logger = LoggerFactory.getLogger(PluginLoader.class);

    private final Ruby ruby = Ruby.newInstance();
    private Map<String, IPlugin> plugins = new LinkedHashMap<>();
    private Set<String> loadedPlugins = new HashSet<>();
    private PluginContext context;

    /**
     * Constructs a new {@link PluginLoader};
     */
    public PluginLoader() {}

    /**
     * Sets the context for the plugin loader.
     *
     * @param context
     *                  The plugin context.
     */
    public void setPluginContext(PluginContext context) {
        this.context = context;
    }

    /**
     * Loads a directory of plugins and initializes the plugin runtime environment.
     *
     * @param directory
     *                      The directory of plugins to load.
     *
     * @throws IOException
     *                      An I/O exception was thrown while loading the plugin directory.
     */
    public void load(String directory) throws IOException {

        // Define the loader in the runtime environment
        ruby.defineVariable(new GlobalVariable(ruby, "$loader", Java.wrapJavaObject(ruby, this)), Scope.GLOBAL);

        // Load the bootstrap and all of its subsequent files
        File bootstrap = new File(directory, "bootstrap.rb");
        ruby.executeScript(getFileContents(bootstrap), "bootstrap");

        File root = new File(directory);

        for(File dir : root.listFiles()) {

            // Skip over the file if its not a directory
            if(!dir.isDirectory()) {
                continue;
            }

            // Check if the plugin script exists in the directory, you only load the 'plugin.rb' file and from there it can
            // require other files as needed
            File file = new File(dir, "plugin.rb");
            if(!file.exists()) {
                continue;
            }

            // Execute the plugin script
            ruby.executeScript(getFileContents(file), dir.getName() + "/plugin");
        }

        for(IPlugin plugin : plugins.values()) {

            // Load each of the plugin dependencies
            for(String dependency : plugin.getDependencies()) {

                if(!plugins.containsKey(dependency)) {
                    throw new InvalidDependencyException("no such dependency '" + dependency + "' requested by '" + plugin.getName() + "' plugin");
                }

                loadPlugin(dependency);
            }

            // Load the plugin
            loadPlugin(plugin.getName());
        }

        logger.info("Loaded " + plugins.size() + " plugins...");
    }

    /**
     * Loads a plugin from its name.
     *
     * @param name
     *              The name of the plugin to load.
     */
    private void loadPlugin(String name) {
        if(loadedPlugins.contains(name)) {
            return;
        }

        IPlugin plugin = plugins.get(name);
        plugin.load();

        loadedPlugins.add(name);
    }

    /**
     * Registers a plugin to the plugin loader.
     *
     * @param plugin
     *                  The plugin to register.
     */
    public void register(IPlugin plugin) {
        plugins.put(plugin.getName(), plugin);
        plugin.setPluginContext(context);
    }

    /**
     * Gets the contents of a file as a string.
     *
     * @param file
     *                      The file to get the contents of.
     *
     * @return
     *                      The file contents as a string.
     *
     * @throws IOException
     *                      An I/O exception was thrown while getting the file contents.
     */
    private static String getFileContents(File file) throws IOException {
        FileReader in = new FileReader(file);
        return IOUtils.toString(in);
    }
}
