package us.evelus.world.model.mob;

import us.evelus.world.interact.InteractionHandler;
import us.evelus.world.model.*;

import java.util.LinkedList;
import java.util.List;

public abstract class Mob extends Entity {

    private List<MobObserver> observers = new LinkedList<>();
    private UpdateTable updateTable = new UpdateTable(this);
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
        updateTable.positionChanged(position);
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
        updateTable.graphicDisplayed(graphic);
    }

    public void animate(Animation animation) {
        updateTable.animated(animation);
    }

    public void synchronize() {
        for(MobObserver observer : observers) {
            updateTable.synchronize(observer);
        }
        updateTable.reset();
    }
}
