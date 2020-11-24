package backend.manager.events.event;

import backend.events.Event;
import backend.side.Side;

public class WinnerDecidedEvent implements Event {

    public Side getWinner() {
        return winner;
    }

    public WinnerDecidedEvent(Side winner)
    {
        this.winner = winner;
    }

    private final Side winner;


}
