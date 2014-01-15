package us.evelus.world.model;

import java.awt.*;

public final class Position {

    public static final int DEFAULT_SCENE_SIZE = 104;

    private int x;
    private int y;
    private int plane;

    public Position() {}

    public Position(int x, int y, int plane) {
        this.x = x;
        this.y = y;
        this.plane = plane;
    }

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

    public int regionX() {
        return x >> 6;
    }

    public int regionY() {
        return y >> 6;
    }

    public int localRegionX() {
        return x - regionX() << 6;
    }

    public int localRegionY() {
        return y - regionY() << 6;
    }

    public int sceneX() {
        return sceneX(DEFAULT_SCENE_SIZE >> 4);
    }

    public int sceneX(int off) {
        return blockX() - off << 3;
    }

    public int sceneY() {
        return sceneY(DEFAULT_SCENE_SIZE >> 4);
    }

    public int sceneY(int off) {
        return blockY() - off << 3;
    }

    public int hash18() {
        return plane() << 16 | regionX() << 8 | regionX();
    }

    public Position getRegionBase() {
        return new Position(regionX(), regionY(), plane);
    }

    public boolean within(Rectangle rectangle) {
        return rectangle.contains(x, y);
    }

    public Rectangle getSceneBounds() {
        return getSceneBounds(DEFAULT_SCENE_SIZE);
    }

    public Rectangle getSceneBounds(int size) {
        return new Rectangle(sceneX(size >> 4), sceneY(size >> 4) - size, size, size);
    }

    public static Position fromHash14(int hash) {
        Position position = new Position();
        position.x     = hash & 0x3F;
        position.y     = hash >> 6 & 0x3F;
        position.plane = hash >> 12 & 0x3;
        return position;
    }

    public static Position fromHash30(int hash) {
        Position position = new Position();
        position.x     = hash & 0x3FFF;
        position.y     = hash >> 14 & 0x3FFF;
        position.plane = hash >> 28 & 0x3;
        return position;
    }
}
