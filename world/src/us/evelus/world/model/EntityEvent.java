package us.evelus.world.model;

import us.evelus.event.Event;
import us.evelus.world.model.observer.Observer;

public abstract class EntityEvent extends Event {

    private final Entity entity;

    public EntityEvent(Entity entity) {
        this.entity = entity;
    }

    public abstract void visit(Observer observer);

    public Entity getEntity() {
        return entity;
    }
}
