package us.evelus.world.model.observer;

import us.evelus.world.model.Entity;
import us.evelus.world.model.mob.Mob;
import us.evelus.world.model.mob.MobEvent;

public final class Observer extends Entity implements EventVisitor {

    @Override
    public void visit(MobEvent event) {

    }

    // TODO
    public void synchronizePositionWith(Mob mob) {

    }
}
