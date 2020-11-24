package backend.manager.events.event;

import backend.events.Event;
import backend.side.Side;

public class GameFinishedEvent implements Event {

    public Side getWinner() {
        return winner;
    }

    public GameFinishedEvent(Side winner)
    {
        this.winner = winner;
    }

    private final Side winner;


}
