package us.evelus.world.interact;

import us.evelus.world.model.Mob;
import us.evelus.world.model.Player;

public abstract class PlayerActionHandler<T extends Mob> {

    private final String action;

    public PlayerActionHandler(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public abstract void handle(T mob, Player target);
}
