package us.evelus.world.model;

public abstract class Entity {

    private Position position;

    public Entity() {}

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
