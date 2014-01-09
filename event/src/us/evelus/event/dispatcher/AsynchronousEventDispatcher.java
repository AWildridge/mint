package us.evelus.event.dispatcher;

import us.evelus.event.Event;
import us.evelus.event.EventHandler;
import us.evelus.util.Worker;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class AsynchronousEventDispatcher extends AbstractEventDispatcher {

    private final Executor executor;

    public AsynchronousEventDispatcher() {
        this(Runtime.getRuntime().availableProcessors());
    }

    public AsynchronousEventDispatcher(int poolSize) {
        executor = Executors.newFixedThreadPool(poolSize);
    }

    private class DispatchWorker extends Worker {

        private EventHandler handler;
        private Event event;

        public DispatchWorker(CountDownLatch latch, EventHandler handler, Event event) {
            super(latch);
            this.handler = handler;
            this.event = event;
        }

        @Override
        public void execute() {
            handler.handle(event);
        }
    }

    @Override
    public void dispatch() {
        CountDownLatch latch = new CountDownLatch(queuedEvents.size());

        for(Event event : queuedEvents) {

            Class eventClass = event.getClass();
            while(eventClass != null) {

                /** Get the handler for the specified event class */
                EventHandler handler = handlers.get(eventClass);

                /** If a handler is registered, handle the specified event */
                if(handler != null) {
                    executor.execute(new DispatchWorker(latch, handler, event));
                    break;
                }

                eventClass = eventClass.getSuperclass();
            }

            if(eventClass == null) {
                throw new RuntimeException("no handler registered for " + eventClass);
            }
        }

        try {
            latch.await();
        } catch(InterruptedException ex) {}

        queuedEvents.clear();
    }
}