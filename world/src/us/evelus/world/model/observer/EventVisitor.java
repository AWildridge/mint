package us.evelus.world.model.observer;

import us.evelus.world.model.mob.MobEvent;

public interface EventVisitor {
    public void visit(MobEvent event);
}
