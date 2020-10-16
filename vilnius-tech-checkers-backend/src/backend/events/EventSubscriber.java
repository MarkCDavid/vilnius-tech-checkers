package backend.events;

public abstract class EventSubscriber<T extends Event> {

    public EventSubscriber(Class<T> eventClass)
    {
        this.eventClass = eventClass;
    }

    public Class<T> getEventClass() {
        return eventClass;
    }

    @SuppressWarnings("unchecked")
    public void capture(Event event) {
        receive((T) event);
    }

    protected abstract void receive(T event);

    private final Class<T> eventClass;
}
