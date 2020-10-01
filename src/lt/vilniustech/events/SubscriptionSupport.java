package lt.vilniustech.events;

import java.util.ArrayList;

public interface SubscriptionSupport {

    void subscribe(EventSubscriber subscriber);
    void unsubscribe(EventSubscriber subscriber);
}
