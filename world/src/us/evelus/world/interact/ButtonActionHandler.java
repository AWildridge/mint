package us.evelus.world.interact;

import us.evelus.world.model.Player;

public abstract class ButtonActionHandler {

    private final int id;

    public ButtonActionHandler(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract void handle(Player player);
}
