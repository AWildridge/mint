package us.evelus.world.model.update;

import us.evelus.event.EventHandler;
import us.evelus.world.model.EntityEvent;
import us.evelus.world.model.EntityList;
import us.evelus.world.model.Position;
import us.evelus.world.model.Observer;

public final class ObserverEventDispatcher extends EventHandler<EntityEvent> {

    private final EntityList<Observer> observerList;

    public ObserverEventDispatcher(EntityList<Observer> observerList) {
        super(EntityEvent.class);
        this.observerList = observerList;
    }

    @Override
    public void handle(EntityEvent event) {
        Position position = event.getEntity().getPosition();
        for(Observer observer : observerList.within(position.getSceneBounds())) {
            event.accept(observer);
        }
    }
}
