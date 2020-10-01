package lt.vilniustech.events;

public abstract class EventSubscriber<T> {

    public EventSubscriber(Class<? extends Event> eventClass)
    {
        this.eventClass = eventClass;
    }

    public Class<? extends Event> getEventClass() {
        return eventClass;
    }

    public void receive(Event event) {
        //noinspection unchecked
        receive((T) event);
    }

    protected abstract void receive(T event);

    private final Class<? extends Event> eventClass;
}
