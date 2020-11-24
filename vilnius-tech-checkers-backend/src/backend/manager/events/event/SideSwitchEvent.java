package backend.manager.events.event;

import backend.events.Event;
import backend.side.Side;

public class SideSwitchEvent implements Event {

    public SideSwitchEvent(Side current)
    {
        this.current = current;
    }

    public Side getCurrent() {
        return current;
    }

    private final Side current;


}
