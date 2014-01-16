package us.evelus.world.model.obj;

import us.evelus.world.model.Entity;

public class SceneObject extends Entity {

    private int type;
    private int orientation;
    private int group;
    private boolean hidden;

    public boolean isInteractable() {
        return true;
    }

    public boolean isWall() {
        return group >= 0 && group < 4;
    }

    public int getType() {
        return type;
    }

    public int getOrientation() {
        return orientation;
    }

    public int getGroup() {
        return group;
    }
}
