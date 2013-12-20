package us.evelus.world.interact;

import us.evelus.world.model.Player;

import java.util.HashMap;
import java.util.Map;

public final class ButtonDispatcher {

    private final Map<Integer, ButtonHandler> handlers = new HashMap<>();

    public ButtonDispatcher() {}

    public void bind(ButtonHandler handler) {
        handlers.put(handler.getId(), handler);
    }

    public void unbind(ButtonHandler handler) {
        handlers.remove(handler.getId());
    }

    public void buttonPressed(Player player, int buttonId) {
        ButtonHandler handler = handlers.get(buttonId);
        if(handler != null) {
            handler.handle(player);
        }
    }
}
