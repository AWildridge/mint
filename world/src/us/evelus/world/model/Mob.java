package us.evelus.world.model;

import us.evelus.world.interact.InteractionHandler;

public abstract class Mob extends Entity {

    private InteractionHandler interactionHandler;
    private int id = -1;

    public void setInteractionHandler(InteractionHandler handler) {
        interactionHandler = handler;
    }

    public void interact(String action, Player target) {
        interactionHandler.handlePlayerInteraction(this, action, target);
    }

    public void interact(String action, GameObject obj) {
        interactionHandler.handleObjInteraction(this, action, obj);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void resetId() {
        id = -1;
    }
}
