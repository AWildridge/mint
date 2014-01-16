package us.evelus.world.model.mob.event;

import us.evelus.world.model.EventVisitor;
import us.evelus.world.model.Player;
import us.evelus.world.model.mob.Mob;
import us.evelus.world.model.mob.MobEvent;

public final class MobActiveEvent extends MobEvent {

    public MobActiveEvent(Mob mob) {
        super(mob);
    }

    @Override
    public void accept(EventVisitor visitor) {
        visitor.visit(this);
    }
}
