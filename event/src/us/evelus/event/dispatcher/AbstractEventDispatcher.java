package us.evelus.event.dispatcher;

import us.evelus.event.Event;
import us.evelus.event.EventHandler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public abstract class AbstractEventDispatcher  {

    protected Map<Class<? extends Event>, EventHandler<?>> handlers = new HashMap<>();
    protected Queue<Event> queuedEvents = new LinkedList<>();

    public void attach(EventHandler<?> handler) {
        handlers.put(handler.getEventClass(), handler);
    }

    public void detach(EventHandler<?> handler) {
        handlers.remove(handler.getEventClass());
    }

    public void queue(Event event) {
        queuedEvents.add(event);
    }

    public abstract void dispatch();
}
