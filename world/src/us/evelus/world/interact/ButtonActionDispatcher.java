package us.evelus.world.interact;

import us.evelus.world.model.Player;

import java.util.HashMap;
import java.util.Map;

public final class ButtonActionDispatcher {

    private final Map<Integer, ButtonActionHandler> handlers = new HashMap<>();

    public ButtonActionDispatcher() {}

    public void bind(ButtonActionHandler handler) {
        handlers.put(handler.getId(), handler);
    }

    public void unbind(ButtonActionHandler handler) {
        handlers.remove(handler.getId());
    }

    public void buttonPressed(Player player, int buttonId) {
        ButtonActionHandler handler = handlers.get(buttonId);
        if(handler != null) {
            handler.handle(player);
        }
    }
}
