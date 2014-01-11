package us.evelus.world.model.update;

import us.evelus.event.dispatcher.AbstractEventDispatcher;
import us.evelus.event.dispatcher.EventDispatcher;
import us.evelus.world.model.EntityEvent;
import us.evelus.world.model.EntityList;
import us.evelus.world.model.observer.SceneObserver;

import java.util.Observer;

public final class UpdateDispatcher {

    private static final int OBSERVER_CAPACITY = 2048;
    private EntityList<SceneObserver> observers = new EntityList<>(OBSERVER_CAPACITY);
    private AbstractEventDispatcher dispatcher = new EventDispatcher();

    public UpdateDispatcher() {
        dispatcher.attach(new ObserverEventDispatcher(observers));
    }

    public void queue(EntityEvent event) {
        dispatcher.queue(event);
    }

    public boolean addObserver(SceneObserver observer) {
        return observers.add(observer);
    }

    public void removeObserver(int id) {
        observers.remove(id);
    }

    public SceneObserver getObserver(int id) {
        return observers.get(id);
    }

    public void tick() {

        // Pre-process all of the observers
        for(SceneObserver observer : observers) {

            // Reset the observers
            observer.reset();
        }

        // Rebuild the entity list quad tree
        observers.rebuild();

        // Dispatch all the queued events
        dispatcher.dispatch();

        // Post-process all of the observers
        for(SceneObserver observer : observers) {

            // Update the observers
            observer.tick();
        }
    }
}
