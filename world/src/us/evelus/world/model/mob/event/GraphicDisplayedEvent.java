package us.evelus.world.model.mob.event;

import us.evelus.world.model.EventVisitor;
import us.evelus.world.model.Graphic;
import us.evelus.world.model.mob.Mob;
import us.evelus.world.model.mob.MobEvent;

public final class GraphicDisplayedEvent extends MobEvent {

    private final Graphic graphic;

    public GraphicDisplayedEvent(Mob mob, Graphic graphic) {
        super(mob);
        this.graphic = graphic;
    }

    @Override
    public void accept(EventVisitor observer) {
        observer.visit(this);
    }

    public Graphic getGraphic() {
        return graphic;
    }
}
