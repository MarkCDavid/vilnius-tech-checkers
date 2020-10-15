package lt.vilniustech.manager.events;

import lt.vilniustech.events.Event;
import lt.vilniustech.moves.base.AbstractMove;

public class MoveProcessedEvent implements Event {

    public AbstractMove getMove() {
        return move;
    }

    public MoveProcessedEvent(AbstractMove move)
    {
        this.move = move;
    }

    private final AbstractMove move;


}
