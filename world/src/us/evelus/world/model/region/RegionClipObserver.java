package us.evelus.world.model.region;

import us.evelus.world.model.Position;
import us.evelus.world.model.obj.SceneObject;
import us.evelus.world.model.pf.TraversalMap;

public final class RegionClipObserver extends RegionObserver {

    private final TraversalMap traversalMap;

    public RegionClipObserver(TraversalMap traversalMap) {
        this.traversalMap = traversalMap;
    }

    @Override
    public void sceneObjectAdded(Region region, Position position, SceneObject object) {
        if(object.isWall()) {
            traversalMap.markWall(object.getOrientation(), position.x(), position.y(), object.getGroup());
        }
    }

    @Override
    public void sceneObjectRemoved(Region region, Position position, SceneObject object) {
        if(object.isWall()) {
            traversalMap.unmarkWall(object.getOrientation(), position.x(), position.y(), object.getGroup());
        }
    }

    @Override
    public void flagSet(Region region, Position position, int flag) {
        if((flag & Region.BLOCKED_FLAG) != 0) {
            traversalMap.markBlocked(position.x(), position.y());
        }
    }
}
