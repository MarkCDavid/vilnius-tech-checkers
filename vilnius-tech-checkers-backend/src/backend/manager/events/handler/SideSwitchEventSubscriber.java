package backend.manager.events.handler;

import backend.events.EventSubscriber;
import backend.manager.events.event.SideSwitchEvent;

public abstract class SideSwitchEventSubscriber extends EventSubscriber<SideSwitchEvent> {
    public SideSwitchEventSubscriber() {
        super(SideSwitchEvent.class);
    }
}
