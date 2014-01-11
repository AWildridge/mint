package us.evelus.world.model.mob.event;

import us.evelus.world.model.EventVisitor;
import us.evelus.world.model.Player;
import us.evelus.world.model.mob.MobEvent;

public final class PlayerBeaconEvent extends MobEvent<Player> {

    public PlayerBeaconEvent(Player plr) {
        super(plr);
    }

    @Override
    public void accept(EventVisitor visitor) {
        visitor.visit(this);
    }
}
