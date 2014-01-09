package us.evelus.world.model.update;

import us.evelus.event.dispatcher.AbstractEventDispatcher;
import us.evelus.event.dispatcher.EventDispatcher;
import us.evelus.world.model.EntityEvent;
import us.evelus.world.model.EntityList;
import us.evelus.world.model.observer.Observer;

public final class UpdateDispatcher {

    private static final int OBSERVER_CAPACITY = 2048;
    private EntityList<Observer> observers = new EntityList<>(OBSERVER_CAPACITY);
    private AbstractEventDispatcher dispatcher = new EventDispatcher();

    public UpdateDispatcher() {
        dispatcher.attach(new ObserverEventDispatcher(observers));
    }

    public void queue(EntityEvent event) {
        dispatcher.queue(event);
    }

    public boolean addObserver(Observer observer) {
        return observers.add(observer);
    }

    public Observer getObserver(int id) {
        return observers.get(id);
    }

    public void tick() {

        // Rebuild the entity list quad tree
        observers.rebuild();

        // Dispatch all the queued events
        dispatcher.dispatch();
    }
}
