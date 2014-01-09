package us.evelus.world.model;

import us.evelus.event.Event;

public abstract class EntityEvent extends Event {

    private final Entity entity;

    public EntityEvent(Entity entity) {
        this.entity = entity;
    }

    public abstract void accept(Observer observer);

    public Entity getEntity() {
        return entity;
    }
}
