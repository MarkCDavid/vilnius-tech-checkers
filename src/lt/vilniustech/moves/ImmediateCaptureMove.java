package lt.vilniustech.moves;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Direction;
import lt.vilniustech.Piece;
import lt.vilniustech.manager.MoveHistorySupport;
import lt.vilniustech.moves.base.CaptureMove;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.moves.finalization.FinalizationArguments;

public class ImmediateCaptureMove extends CaptureMove {

    public ImmediateCaptureMove(Coordinate from, Coordinate over, Coordinate to) {
        super(from, over, to);
    }

    public ImmediateCaptureMove(Coordinate from, Direction direction, int moveSize, int jumpSize) {
        super(from, direction, moveSize, jumpSize);
    }

    @Override
    public void apply(Board board) {
        if(isApplied())
            return;

        Piece overPiece = board.getPiece(this.over);

        if(overPiece == null)
            return;

        applied = true;

        capturedPiece = board.popPiece(over);
        unpromotedPiece = board.popPiece(from);
        board.putPiece(to, promotionMove ? unpromotedPiece.promote() : unpromotedPiece);
    }

    @Override
    public void revert(Board board) {
        if(!isApplied())
            return;

        Piece overPiece = board.getPiece(this.over);

        if(overPiece != null)
            return;

        applied = false;

        board.putPiece(over, capturedPiece);

        board.popPiece(to);
        board.putPiece(from, unpromotedPiece);
    }


    @Override
    public Move finalizeMove(Board board, MoveHistorySupport history, FinalizationArguments argumentType) {
        promotionMove = argumentType.isPromote();
        return this;
    }


    private Piece capturedPiece;
}
