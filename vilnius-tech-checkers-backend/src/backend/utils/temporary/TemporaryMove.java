package backend.utils.temporary;

import backend.Board;
import backend.moves.base.Move;

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
