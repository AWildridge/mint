package us.evelus.world.interact;

import us.evelus.world.model.mob.Mob;
import us.evelus.world.model.Player;

import java.util.HashMap;
import java.util.Map;

public final class PlayerActionDispatcher {

    private Map<String, PlayerActionHandler> handlers = new HashMap<>();

    public PlayerActionDispatcher() {}

    public void bind(PlayerActionHandler handler) {
        handlers.put(handler.getAction(), handler);
    }

    public void unbind(PlayerActionHandler handler) {
        handlers.remove(handler.getAction());
    }

    public void handle(Mob mob, String action, Player target) {
        PlayerActionHandler handler = handlers.get(action);
        if(handler != null) {
            handler.handle(mob, target);
        }
    }
}
