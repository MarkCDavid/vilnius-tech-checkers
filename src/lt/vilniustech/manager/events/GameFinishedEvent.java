package lt.vilniustech.manager.events;

import lt.vilniustech.Side;
import lt.vilniustech.events.Event;

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
