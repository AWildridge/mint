package us.evelus.world.interact;

import us.evelus.world.model.Player;

public abstract class ButtonHandler {

    private final int id;

    public ButtonHandler(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract void handle(Player player);
}
