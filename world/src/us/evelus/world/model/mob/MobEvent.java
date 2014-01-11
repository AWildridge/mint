package us.evelus.world.model.mob;

import us.evelus.world.model.EntityEvent;

public abstract class MobEvent<T extends Mob> extends EntityEvent {

    private final T mob;

    public MobEvent(T mob) {
        super(mob);
        this.mob = mob;
    }

    public T getMob() {
        return mob;
    }
}
