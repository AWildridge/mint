package us.evelus.world.model.mob;

import us.evelus.world.model.EntityEvent;
import us.evelus.world.model.observer.Observer;

public abstract class MobEvent extends EntityEvent {

    private final Mob mob;

    public MobEvent(Mob mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public void visit(Observer observer) {
        observer.visit(this);
    }

    public Mob getMob() {
        return mob;
    }
}
