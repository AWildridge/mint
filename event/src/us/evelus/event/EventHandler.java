package us.evelus.event;

public abstract class EventHandler<T extends Event> {

    private Class<? extends T> clazz;

    public EventHandler(Class clazz) {
        this.clazz = clazz;
    }

    public abstract void handle(T event);

    public Class<? extends T> getEventClass() {
        return clazz;
    }
}