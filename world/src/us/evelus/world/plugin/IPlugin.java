package us.evelus.world.plugin;

public interface IPlugin {
    public void setPluginContext(PluginContext context);
    public void load();
    public void unload();
    public String[] getAuthors();
    public String[] getDependencies();
    public String getDescription();
    public String getName();
}
