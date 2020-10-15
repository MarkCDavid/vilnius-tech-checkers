package lt.vilniustech.utils.temporary;

import lt.vilniustech.Board;
import lt.vilniustech.moves.base.Move;

public class TemporaryMove implements AutoCloseable {

    public TemporaryMove(Board board, Move move) {
        this.board = board;
        this.move = move;

        move.apply(board);
    }

    @Override
    public void close() {
        move.revert(board);
    }

    private final Board board;
    private final Move move;
}
