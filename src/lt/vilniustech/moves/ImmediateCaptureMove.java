package lt.vilniustech.moves;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Direction;
import lt.vilniustech.Piece;
import lt.vilniustech.manager.MoveHistory;
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
    public boolean hasUncaptured() {
        return false;
    }

    @Override
    public void apply(Board board) {
        super.apply(board);

        capturedPiece = board.popPiece(over);
        unpromotedPiece = board.popPiece(from);
        board.putPiece(to, promotionMove ? unpromotedPiece.promote() : unpromotedPiece);
    }

    @Override
    public void revert(Board board) {
        super.revert(board);

        board.putPiece(over, capturedPiece);

        board.popPiece(to);
        board.putPiece(from, unpromotedPiece);
    }


    @Override
    public Move finalizeMove(Board board, MoveHistory history, FinalizationArguments argumentType) {
        promotionMove = argumentType.isPromote();
        return this;
    }
}
