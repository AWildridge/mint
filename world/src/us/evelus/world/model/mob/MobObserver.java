package us.evelus.world.model.mob;

import us.evelus.world.model.Animation;
import us.evelus.world.model.Graphic;
import us.evelus.world.model.Position;

public interface MobObserver {
    public void positionUpdated(Mob mob, Position position);
    public void graphicDisplayed(Mob mob, Graphic graphic);
    public void animated(Mob mob, Animation animation);
    public void teleported(Mob mob);
    public void modified(Mob mob);
    public void inactive(Mob mob);
}
