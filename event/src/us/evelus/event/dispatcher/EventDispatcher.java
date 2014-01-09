package us.evelus.event.dispatcher;

import us.evelus.event.Event;
import us.evelus.event.EventHandler;

public final class EventDispatcher extends AbstractEventDispatcher {

    /**
     * Dispatches all the queued events to their handler if one exists.
     */
    @Override
    public void dispatch() {
        for(Event event : queuedEvents) {

            Class eventClass = event.getClass();

            while(eventClass != null) {

                EventHandler handler = handlers.get(eventClass);

                if(handler != null) {
                    handler.handle(event);
                    break;
                }

                eventClass = eventClass.getSuperclass();
            }
        }

        queuedEvents.clear();
    }
}
