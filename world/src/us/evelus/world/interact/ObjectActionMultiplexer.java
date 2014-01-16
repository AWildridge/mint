package us.evelus.world.interact;

import us.evelus.world.model.obj.SceneObject;
import us.evelus.world.model.mob.Mob;

import java.util.HashMap;
import java.util.Map;

public final class ObjectActionMultiplexer {

    private final Map<Integer, ObjectActionHandler> handlers = new HashMap<>();

    public void bind(ObjectActionHandler handler) {
        for(int id : handler.getIds()) {
            handlers.put(id, handler);
        }
    }

    @SuppressWarnings("unchecked")
    public void handle(Mob mob, SceneObject object) {
        ObjectActionHandler handler = handlers.get(object.getType());
        if(handler != null) {
            handler.handle(mob, object);
        }
    }
}
