package lt.vilniustech.events;

import java.util.*;

public class EventEmitter implements SubscriptionSupport {

    public EventEmitter() {
        subscribers = new HashMap<>();
    }

    public void emit(Event event) {
        if(!subscribers.containsKey(event.getClass()))
            return;

        for(EventSubscriber subscriber : subscribers.get(event.getClass())) {
            subscriber.receive(event);
        }
    }

    public void subscribe(EventSubscriber subscriber) {
        if(!subscribers.containsKey(subscriber.getEventClass()))
            subscribers.put(subscriber.getEventClass(), new ArrayList<>());

        subscribers.get(subscriber.getEventClass()).add(subscriber);
    }

    public void unsubscribe(EventSubscriber subscriber) {
        if(subscribers.containsKey(subscriber.getEventClass()))
            subscribers.get(subscriber.getEventClass()).remove(subscriber);
    }


    Map<Class<? extends Event>, List<EventSubscriber>> subscribers;
}
