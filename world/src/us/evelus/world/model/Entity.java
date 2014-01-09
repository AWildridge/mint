package us.evelus.world.model;

public abstract class Entity {

    private Position position = new Position();  // TEMP
    private int id;

    public Entity() {}

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
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
