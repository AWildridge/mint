package us.evelus.world.model.mob;

import us.evelus.world.model.Animation;
import us.evelus.world.model.Graphic;
import us.evelus.world.model.Position;

public abstract class MobObserverAdapter implements MobObserver {

    @Override
    public void positionUpdated(Mob mob, Position position) {}

    @Override
    public void graphicDisplayed(Mob mob, Graphic graphic) {}

    @Override
    public void animated(Mob mob, Animation animation) {}

    @Override
    public void teleported(Mob mob) {}

    @Override
    public void modified(Mob mob) { }

    @Override
    public void active(Mob mob) {}

    @Override
    public void inactive(Mob mob) {}
}
