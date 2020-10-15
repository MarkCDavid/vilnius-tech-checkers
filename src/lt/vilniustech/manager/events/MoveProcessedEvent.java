package lt.vilniustech.manager.events;

import lt.vilniustech.events.Event;
import lt.vilniustech.moves.base.Move;

public class MoveProcessedEvent implements Event {

    public Move getMove() {
        return move;
    }

    public MoveProcessedEvent(Move move)
    {
        this.move = move;
    }

    private final Move move;


}
