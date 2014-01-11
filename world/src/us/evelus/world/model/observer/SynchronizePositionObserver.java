package us.evelus.world.model.observer;

import us.evelus.world.model.Position;
import us.evelus.world.model.mob.Mob;
import us.evelus.world.model.mob.MobObserverAdapter;

public final class SynchronizePositionObserver extends MobObserverAdapter {

    private final SceneObserver observer;

    public SynchronizePositionObserver(SceneObserver observer) {
        this.observer = observer;
    }

    @Override
    public void positionUpdated(Mob mob, Position position) {
        observer.setPosition(position);
    }

    @Override
    public void inactive(Mob mob) {
        mob.detach(this);
    }
}
