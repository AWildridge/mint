package us.evelus.world.plugin;

import us.evelus.world.interact.*;
import us.evelus.world.model.World;

public final class PluginContext {

    private final InteractionHandler interactionHandler;
    private final PluginHelper helper;

    public PluginContext(World world) {
        interactionHandler = world.getInteractionHandler();
        helper = new PluginHelper(world);
    }

    public void bind(ButtonActionHandler handler) {
        interactionHandler.getButtonDispatcher().bind(handler);
    }

    public void unbind(ButtonActionHandler handler) {
        interactionHandler.getButtonDispatcher().unbind(handler);
    }

    public PluginHelper getHelper() {
        return helper;
    }
}
