package lt.vilniustech.manager.events;

import lt.vilniustech.events.Event;
import lt.vilniustech.side.Side;

public class GameFinishedEvent implements Event {

    public String getWinner() {
        return winner;
    }

    public GameFinishedEvent(String winner)
    {
        this.winner = winner;
    }

    private final String winner;


}
