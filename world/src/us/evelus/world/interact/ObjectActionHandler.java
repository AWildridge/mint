package us.evelus.world.interact;

import us.evelus.world.model.GameObject;
import us.evelus.world.model.Mob;

public abstract class ObjectActionHandler<T extends Mob> {

    private final String action;
    private int[] ids;

    public ObjectActionHandler(String action, int... ids) {
        this.action = action;
        this.ids = ids;
    }

    public String getAction() {
        return action;
    }

    public int[] getIds() {
        return ids;
    }

    public abstract void handle(T mob, GameObject object);
}
