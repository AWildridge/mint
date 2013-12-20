package us.evelus.world.interact;

import us.evelus.world.model.Mob;

public abstract class MobActionHandler<M extends Mob, T extends Mob> {

    private final String action;

    public MobActionHandler(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public abstract void handle(M mob, T target);
}
