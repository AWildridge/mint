package us.evelus.world.interact;

import us.evelus.world.model.Mob;

import java.util.HashMap;
import java.util.Map;

public final class MobActionDispatcher {

    private Map<String, MobActionHandler> handlers = new HashMap<>();

    public MobActionDispatcher() {}

    public void bind(MobActionHandler handler) {
        handlers.put(handler.getAction(), handler);
    }

    public void unbind(MobActionHandler handler) {
        handlers.remove(handler.getAction());
    }

    public void handle(Mob mob, String action, Mob target) {
        MobActionHandler handler = handlers.get(action);
        if(handler != null) {
            handler.handle(mob, target);
        }
    }
}
