package us.evelus.world.model.mob;

import us.evelus.world.model.Animation;
import us.evelus.world.model.Graphic;
import us.evelus.world.model.Position;

public final class QueuedUpdates {

    public static final int POSITION_CHANGED   = 0x01;
    public static final int ANIMATED           = 0x02;
    public static final int GRAPHIC_DISPLAYED  = 0x04;
    public static final int TELEPORTED         = 0x08;
    public static final int TRAVERSED          = 0x10;

    private final Mob mob;
    private Position position;
    private Animation animation;
    private Graphic graphic;
    private Direction firstDir;
    private Direction secondDir;
    private int flags;

    public QueuedUpdates(Mob mob) {
        this.mob = mob;
    }

    public void walkedDirection(Direction first, Direction second) {
        if((flags & TELEPORTED) != 0) {
            System.out.println("NOT SURE WHAT TO DO HERE YET");
            return;
        }
        firstDir = first;
        secondDir = second;
        flags |= TRAVERSED;
    }

    public void positionChanged(Position position) {
        this.position = position;
        flags |= POSITION_CHANGED;
    }

    public void animated(Animation animation) {
        this.animation = animation;
        flags |= ANIMATED;
    }

    public void graphicDisplayed(Graphic graphic) {
        this.graphic = graphic;
        flags |= GRAPHIC_DISPLAYED;
    }

    public void teleported() {
        flags |= TELEPORTED;
    }

    public void synchronize(MobObserver observer) {
        if((flags & POSITION_CHANGED) != 0) {
            observer.positionUpdated(mob, position);
        }

        if((flags & ANIMATED) != 0) {
            observer.animated(mob, animation);
        }

        if((flags & GRAPHIC_DISPLAYED) != 0) {
            observer.graphicDisplayed(mob, graphic);
        }

        if((flags & TELEPORTED) != 0) {
            observer.teleported(mob);
        }
    }

    public void reset() {
        position = null;
        animation = null;
        graphic = null;
        flags = 0;
    }
}
