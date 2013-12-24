package us.evelus.world.interact;

import us.evelus.world.model.GameObject;
import us.evelus.world.model.Mob;

import java.util.HashMap;
import java.util.Map;

public final class ObjectActionMultiplexer {

    private final Map<Integer, ObjectActionHandler> handlers = new HashMap<>();

    public void bind(ObjectActionHandler handler) {
        for(int id : handler.getIds()) {
            handlers.put(id, handler);
        }
    }

    public void handle(Mob mob, GameObject object) {
        ObjectActionHandler handler = handlers.get(object.getType());
        if(handler != null) {
            handler.handle(mob, object);
        }
    }
}
