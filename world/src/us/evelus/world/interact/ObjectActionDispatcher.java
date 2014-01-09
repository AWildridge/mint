package us.evelus.world.interact;

import us.evelus.world.model.WorldObject;
import us.evelus.world.model.mob.Mob;

import java.util.HashMap;
import java.util.Map;

public final class ObjectActionDispatcher {

    private Map<String, ObjectActionMultiplexer> multiplexers = new HashMap<>();

    public ObjectActionDispatcher() {}

    public void bind(ObjectActionHandler handler) {
        ObjectActionMultiplexer multiplexer = multiplexers.get(handler.getAction());
        if(multiplexer == null) {
            multiplexer = new ObjectActionMultiplexer();
            multiplexers.put(handler.getAction(), multiplexer);
        }
        multiplexer.bind(handler);
    }

    public void handle(Mob mob, String action, WorldObject object) {
        ObjectActionMultiplexer multiplexer = multiplexers.get(action);
        if(multiplexer != null) {
            multiplexer.handle(mob, object);
        }
    }
}
