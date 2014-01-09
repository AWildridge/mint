package us.evelus.world.model;

import us.evelus.world.model.mob.Mob;
import us.evelus.world.model.mob.MobEvent;

public final class Observer extends Entity implements EntityEventVisitor {

    @Override
    public void visit(MobEvent event) {

    }

    // TODO
    public void synchronizePositionWith(Mob mob) {
        setPosition(mob.getPosition());
    }
}
