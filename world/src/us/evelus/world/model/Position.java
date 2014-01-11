package us.evelus.world.model;

import java.awt.*;

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

    public int blockX() {
        return x >> 3;
    }

    public int blockY() {
        return y >> 3;
    }

    public int sceneX() {
        return blockX() - 6 << 3;
    }

    public int sceneY() {
        return blockY() - 6 << 3;
    }

    public boolean within(Rectangle rectangle) {
        return rectangle.contains(x, y);
    }

    public Rectangle getSceneBounds() {
        return new Rectangle(sceneX(), sceneY(), 104, 104);
    }

    public static Position fromHash30(int hash) {
        Position position = new Position();
        position.x     = hash & 0x3FFF;
        position.y     = hash >> 14 & 0x3FFF;
        position.plane = hash >> 28 & 0x3;
        return position;
    }
}
