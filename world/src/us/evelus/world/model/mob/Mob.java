package us.evelus.world.model.mob;

import us.evelus.world.interact.InteractionHandler;
import us.evelus.world.model.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class Mob extends Entity {

    private List<MobObserver> observers = new LinkedList<>();
    private QueuedUpdates queuedUpdates = new QueuedUpdates(this);
    private InteractionHandler interactionHandler;

    protected Mob() {}

    public void attach(MobObserver observer) {
        observers.add(observer);
    }

    public void detach(MobObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void setPosition(Position position) {
        queuedUpdates.positionChanged(position);
        super.setPosition(position);
    }

    public void setInteractionHandler(InteractionHandler handler) {
        interactionHandler = handler;
    }

    public void interact(String action, Player target) {
        interactionHandler.handlePlayerInteraction(this, action, target);
    }

    public void interact(String action, SceneObject obj) {
        interactionHandler.handleObjInteraction(this, action, obj);
    }

    public void displayGraphic(Graphic graphic) {
        queuedUpdates.graphicDisplayed(graphic);
    }

    public void animate(Animation animation) {
        queuedUpdates.animated(animation);
    }

    public void teleport(Position position) {
        setPosition(position);
        queuedUpdates.teleported();
    }

    public void tick() {
        //queuedUpdates.walkedDirection(Direction.NONE, Direction.NONE);
    }

    public void synchronize() {
        for(MobObserver observer : observers) {
            queuedUpdates.synchronize(observer);
        }
        queuedUpdates.reset();
    }

    public void resetId() {
        for(MobObserver observer : observers) {
            observer.inactive(this);
        }
        super.resetId();
    }
}
