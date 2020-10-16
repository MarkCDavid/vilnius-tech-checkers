package backend.utils.temporary;

import backend.Board;
import backend.Piece;
import backend.moves.base.Move;

public class TemporaryPromotion implements AutoCloseable {

    public TemporaryPromotion(Board board, Move move) {
        this.board = board;
        this.move = move;
        this.unpromoted = board.popPiece(move.getTo());
        board.putPiece(move.getTo(), unpromoted.promote());
    }

    @Override
    public void close() {
        board.putPiece(move.getTo(), unpromoted);
    }

    private final Board board;
    private final Move move;
    private final Piece unpromoted;
}
