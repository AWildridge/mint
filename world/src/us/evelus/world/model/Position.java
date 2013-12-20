package us.evelus.world.model;

public final class Position {

    private int x;
    private int y;
    private int plane;

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int plane() {
        return plane;
    }

    public static Position fromHash30(int hash) {
        Position position = new Position();
        position.x     = hash & 0x3FFF;
        position.y     = hash >> 14 & 0x3FFF;
        position.plane = hash >> 28 & 0x3;
        return position;
    }
}
