package backend.events;

public interface SubscriptionSupport {

    void subscribe(EventSubscriber<?> subscriber);
    void unsubscribe(EventSubscriber<?> subscriber);
}
