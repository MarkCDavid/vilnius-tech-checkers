package lt.vilniustech.utils;

import lt.vilniustech.manager.MoveHistorySupport;
import lt.vilniustech.moves.base.Move;

public class TemporaryAddHistory implements AutoCloseable {

    public TemporaryAddHistory(MoveHistorySupport history, Move move) {
        this.history = history;
        this.move = move;

        if(history.getMoveHistory() != null)
            history.getMoveHistory().add(move);
    }

    @Override
    public void close() {

        if(history.getMoveHistory() != null)
            history.getMoveHistory().remove(move);
    }

    private final MoveHistorySupport history;
    private final Move move;
}
