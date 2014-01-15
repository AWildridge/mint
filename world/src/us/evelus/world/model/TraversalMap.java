package us.evelus.world.model;

import static us.evelus.world.model.OrientationConstants.*;

public final class TraversalMap {

    public static final int WALL_NORTH       = 0x01, WALL_SOUTH      = 0x02,
                            WALL_EAST        = 0x04, WALL_WEST       = 0x08,
                            WALL_NORTH_EAST  = 0x10, WALL_NORTH_WEST = 0x20,
                            WALL_SOUTH_EAST  = 0x40, WALL_SOUTH_WEST = 0x80,

                            OCCUPANT = 0x8000, IMPENETRABLE_OCCUPANT = 0x10000,

                            IMPENETRABLE_WALL_NORTH       = 0x100,  IMPENETRABLE_WALL_SOUTH      = 0x200,
                            IMPENETRABLE_WALL_EAST        = 0x400,  IMPENETRABLE_WALL_WEST       = 0x800,
                            IMPENETRABLE_WALL_NORTH_EAST  = 0x800,  IMPENETRABLE_WALL_NORTH_WEST = 0x1000,
                            IMPENETRABLE_WALL_SOUTH_EAST  = 0x2000, IMPENETRABLE_WALL_SOUTH_WEST = 0x4000,

                            BLOCKED = 0x20000, NONE = 0x0;

    private int[] flags;
    private int size;

    public TraversalMap(int size) {
        flags = new int[size * size];
        this.size = size;
    }

    public void set(int x, int y, int flag) {
        flags[x * size + y] |= flag;
    }

    public void unset(int x, int y, int flag) {
        flags[x * size + y] &= 0xffff - flag;
    }

    public void markBlocked(int x, int y) {
        set(x, y, BLOCKED);
    }

    public void markWall(int orientation, int x, int y, int group) {
        switch(group) {
            case 0:
                if (orientation == WEST) {
                    set(x, y, WALL_WEST);
                    set(x - 1, y, WALL_EAST);
                }
                if (orientation == NORTH) {
                    set(x, y, WALL_NORTH);
                    set(x, y + 1, WALL_SOUTH);
                }
                if (orientation == EAST) {
                    set(x, y, WALL_EAST);
                    set(x + 1, y, WALL_WEST);
                }
                if (orientation == SOUTH) {
                    set(x, y, WALL_SOUTH);
                    set(x, y - 1, WALL_NORTH);
                }
                break;

            case 2:
                if (orientation == WEST) {
                    set(x, y, WALL_WEST | WALL_NORTH);
                    set(x - 1, y, WALL_EAST);
                    set(x, y + 1, WALL_SOUTH);
                }
                if (orientation == NORTH) {
                    set(x, y, WALL_EAST | WALL_NORTH);
                    set(x, y + 1, WALL_SOUTH);
                    set(x + 1, y, WALL_WEST);
                }
                if (orientation == EAST) {
                    set(x, y, WALL_EAST | WALL_SOUTH);
                    set(x + 1, y, WALL_WEST);
                    set(x, y - 1, WALL_NORTH);
                }
                if (orientation == SOUTH) {
                    set(x, y, WALL_EAST | WALL_SOUTH);
                    set(x, y - 1, WALL_WEST);
                    set(x - 1, y, WALL_NORTH);
                }
                break;

            case 1:
            case 3:
                if (orientation == WEST) {
                    set(x, y, WALL_NORTH_WEST);
                    set(x - 1, y + 1, WALL_SOUTH_EAST);
                }
                if (orientation == NORTH) {
                    set(x, y, WALL_NORTH_EAST);
                    set(x + 1, y + 1, WALL_SOUTH_WEST);
                }
                if (orientation == EAST) {
                    set(x, y, WALL_SOUTH_EAST);
                    set(x + 1, y - 1, WALL_NORTH_WEST);
                }
                if (orientation == SOUTH) {
                    set(x, y, WALL_SOUTH_WEST);
                    set(x - 1, y - 1, WALL_SOUTH_WEST);
                }
                break;
        }
    }

    public void unmarkWall(int orientation, int x, int y, int group) {
        switch(group) {
            case 0:
                if (orientation == WEST) {
                    unset(x, y, WALL_WEST);
                    unset(x - 1, y, WALL_EAST);
                }
                if (orientation == NORTH) {
                    unset(x, y, WALL_NORTH);
                    unset(x, y + 1, WALL_SOUTH);
                }
                if (orientation == EAST) {
                    unset(x, y, WALL_EAST);
                    unset(x + 1, y, WALL_WEST);
                }
                if (orientation == SOUTH) {
                    unset(x, y, WALL_SOUTH);
                    unset(x, y - 1, WALL_NORTH);
                }
                break;

            case 2:
                if (orientation == WEST) {
                    unset(x, y, WALL_WEST | WALL_NORTH);
                    unset(x - 1, y, WALL_EAST);
                    unset(x, y + 1, WALL_SOUTH);
                }
                if (orientation == NORTH) {
                    unset(x, y, WALL_EAST | WALL_NORTH);
                    unset(x, y + 1, WALL_SOUTH);
                    unset(x + 1, y, WALL_WEST);
                }
                if (orientation == EAST) {
                    unset(x, y, WALL_EAST | WALL_SOUTH);
                    unset(x + 1, y, WALL_WEST);
                    unset(x, y - 1, WALL_NORTH);
                }
                if (orientation == SOUTH) {
                    unset(x, y, WALL_EAST | WALL_SOUTH);
                    unset(x, y - 1, WALL_WEST);
                    unset(x - 1, y, WALL_NORTH);
                }
                break;

            case 1:
            case 3:
                if (orientation == WEST) {
                    unset(x, y, WALL_NORTH_WEST);
                    unset(x - 1, y + 1, WALL_SOUTH_EAST);
                }
                if (orientation == NORTH) {
                    unset(x, y, WALL_NORTH_EAST);
                    unset(x + 1, y + 1, WALL_SOUTH_WEST);
                }
                if (orientation == EAST) {
                    unset(x, y, WALL_SOUTH_EAST);
                    unset(x + 1, y - 1, WALL_NORTH_WEST);
                }
                if (orientation == SOUTH) {
                    unset(x, y, WALL_SOUTH_WEST);
                    unset(x - 1, y - 1, WALL_SOUTH_WEST);
                }
                break;
        }
    }
}
