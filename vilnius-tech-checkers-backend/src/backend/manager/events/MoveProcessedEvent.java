package backend.manager.events;

import backend.events.Event;
import backend.moves.base.Move;

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
