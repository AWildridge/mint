package us.evelus.world.model.region;

import us.evelus.world.model.Position;
import us.evelus.world.model.SceneObject;

public abstract class RegionObserver {
    public abstract void sceneObjectAdded(Region region, Position position, SceneObject object);
    public abstract void sceneObjectRemoved(Region region, Position position, SceneObject object);
    public abstract void flagSet(Region region, Position position, int flag);
}
