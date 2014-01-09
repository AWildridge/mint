package us.evelus.world.model.mob;

import us.evelus.world.model.Animation;
import us.evelus.world.model.Graphic;
import us.evelus.world.model.Position;

public final class UpdateTable {

    public static int POSITION_CHANGED   = 0x01;
    public static int MOB_ANIMATED       = 0x02;
    public static int GRAPHIC_DISPLAYED  = 0x04;

    private final Mob mob;
    private Position position;
    private Animation animation;
    private Graphic graphic;
    private int flags;

    public UpdateTable(Mob mob) {
        this.mob = mob;
    }

    public void positionChanged(Position position) {
        this.position = position;
        flags |= POSITION_CHANGED;
    }

    public void animated(Animation animation) {
        this.animation = animation;
        flags |= MOB_ANIMATED;
    }

    public void graphicDisplayed(Graphic graphic) {
        this.graphic = graphic;
        flags |= GRAPHIC_DISPLAYED;
    }

    public void synchronize(MobObserver observer) {
        if((flags & POSITION_CHANGED) != 0) {
            observer.positionUpdated(mob, position);
        }

        if((flags & MOB_ANIMATED) != 0) {
            observer.animated(mob, animation);
        }

        if((flags & GRAPHIC_DISPLAYED) != 0) {
            observer.graphicDisplayed(mob, graphic);
        }
    }

    public void reset() {
        position = null;
        animation = null;
        graphic = null;
        flags = 0;
    }
}
