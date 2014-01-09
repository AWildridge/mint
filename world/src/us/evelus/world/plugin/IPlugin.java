package us.evelus.world.plugin;

public interface IPlugin {
    public void setPluginContext(PluginContext context);
    public void load();
    public void unload();
    public String[] authors();
    public String[] dependencies();
    public String name();
}
                        cd