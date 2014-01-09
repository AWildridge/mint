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

    private final Ruby runtime = Ruby.newInstance();
    private Map<String, IPlugin> plugins = new LinkedHashMap<>();
    private Set<String> loadedPlugins = new HashSet<>();
    private PluginContext context;

    public PluginLoader() {}

    public void setPluginContext(PluginContext context) {
        this.context = context;
    }

    public void load(String directory) throws IOException {

        // Define the loader in the runtime environment
        runtime.defineVariable(new GlobalVariable(runtime, "$loader", Java.wrapJavaObject(runtime, this)), Scope.GLOBAL);

        // Load the bootstrap and all of its subsequent files
        File bootstrap = new File(directory, "bootstrap.rb");
        runtime.executeScript(getFileContents(bootstrap), "bootstrap");

        File root = new File(directory);

        for(File dir : root.listFiles()) {

            // Skip over the file if its not a directory
            if(!dir.isDirectory()) {
                continue;
            }

            // Check if the plugin script exists in the directory
            File file = new File(dir, "plugin.rb");
            if(!file.exists()) {
                continue;
            }

            // Execute the plugin script
            runtime.executeScript(getFileContents(file), dir.getName() + "/plugin");
        }

        for(IPlugin plugin : plugins.values()) {

            // Load each of the plugin dependencies
            for(String dependency : plugin.dependencies()) {

                if(!plugins.containsKey(dependency)) {
                    throw new InvalidDependencyException("no such dependency '" + dependency + "' requested by '" + plugin.name() + "' plugin");
                }

                loadPlugin(dependency);
            }

            // Load the plugin
            loadPlugin(plugin.name());
        }

        logger.info("Loaded " + plugins.size() + " plugins...");
    }

    private void loadPlugin(String name) {
        if(loadedPlugins.contains(name)) {
            return;
        }

        IPlugin plugin = plugins.get(name);
        plugin.load();

        loadedPlugins.add(name);
    }

    public void register(IPlugin plugin) {
        plugins.put(plugin.name(), plugin);
        plugin.setPluginContext(context);
    }

    private static String getFileContents(File file) throws IOException {
        FileReader in = new FileReader(file);
        return IOUtils.toString(in);
    }
}
