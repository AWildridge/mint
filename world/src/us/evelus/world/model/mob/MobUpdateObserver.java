package us.evelus.world.model.mob;

import us.evelus.world.model.Animation;
import us.evelus.world.model.Graphic;
import us.evelus.world.model.mob.event.AnimatedEvent;
import us.evelus.world.model.mob.event.GraphicDisplayedEvent;
import us.evelus.world.model.mob.event.TeleportedEvent;
import us.evelus.world.model.update.UpdateDispatcher;

public final class MobUpdateObserver extends MobObserverAdapter {

    private final UpdateDispatcher dispatcher;

    public MobUpdateObserver(UpdateDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void graphicDisplayed(Mob mob, Graphic graphic) {
        dispatcher.queue(new GraphicDisplayedEvent(mob, graphic));
    }

    @Override
    public void animated(Mob mob, Animation animation) {
        dispatcher.queue(new AnimatedEvent(mob));
    }

    @Override
    public void teleported(Mob mob) {
        dispatcher.queue(new TeleportedEvent(mob));
    }
}