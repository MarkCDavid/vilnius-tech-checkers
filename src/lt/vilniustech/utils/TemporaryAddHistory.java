package lt.vilniustech.utils;

import lt.vilniustech.moves.MoveHistory;
import lt.vilniustech.moves.base.Move;

public class TemporaryAddHistory implements AutoCloseable {

    public TemporaryAddHistory(MoveHistory history, Move move) {
        this.history = history;
        this.move = move;
        history.add(move);
    }

    @Override
    public void close() {
        history.remove(move);
    }

    private final MoveHistory history;
    private final Move move;
}
