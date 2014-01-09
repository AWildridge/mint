package us.evelus.world.model;

import us.evelus.world.model.mob.MobEvent;

public interface EntityEventVisitor {
    public void visit(MobEvent event);
}
