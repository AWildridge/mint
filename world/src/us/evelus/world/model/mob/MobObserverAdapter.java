package us.evelus.world.model.mob;

import us.evelus.world.model.Animation;
import us.evelus.world.model.Graphic;
import us.evelus.world.model.Position;

public abstract class MobObserverAdapter implements MobObserver {

    @Override
    public void positionUpdated(Mob mob, Position position) {
        modified(mob);
    }

    @Override
    public void graphicDisplayed(Mob mob, Graphic graphic) {
        modified(mob);
    }

    @Override
    public void animated(Mob mob, Animation animation) {
        modified(mob);
    }

    public void modified(Mob mob) {}
}
