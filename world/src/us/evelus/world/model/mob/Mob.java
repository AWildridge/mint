package us.evelus.world.model.mob;

import us.evelus.world.interact.InteractionHandler;
import us.evelus.world.model.Entity;
import us.evelus.world.model.WorldObject;
import us.evelus.world.model.Player;

import java.util.LinkedList;
import java.util.List;

public abstract class Mob extends Entity {

    private List<MobObserver> observers = new LinkedList<>();
    private InteractionHandler interactionHandler;
    private int id = -1;

    protected Mob() {}

    public void attach(MobObserver observer) {
        observers.add(observer);
    }

    public void detach(MobObserver observer) {
        observers.remove(observer);
    }

    public void setInteractionHandler(InteractionHandler handler) {
        interactionHandler = handler;
    }

    public void interact(String action, Player target) {
        interactionHandler.handlePlayerInteraction(this, action, target);
    }

    public void interact(String action, WorldObject obj) {
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
