package backend.moves;

import backend.*;
import backend.Coordinate;
import backend.Direction;
import backend.Board;
import backend.manager.MoveHistory;
import backend.moves.base.CaptureMove;
import backend.moves.base.Move;
import backend.moves.finalization.FinalizationArguments;

public class NonImmediateCaptureMove extends CaptureMove {

    public NonImmediateCaptureMove(Coordinate from, Coordinate over, Coordinate to) {
        super(from, over, to);
    }

    public NonImmediateCaptureMove(Coordinate from, Direction direction, int moveSize, int jumpSize) {
        super(from, direction, moveSize, jumpSize);
    }

    @Override
    public boolean hasUncaptured() {
        return true;
    }

    @Override
    public void apply(Board board) {
        super.apply(board);

        capturedPiece = board.getPiece(over);
        unpromotedPiece = board.popPiece(from);
        board.putPiece(to, promotionMove ? unpromotedPiece.promote() : unpromotedPiece);
    }

    @Override
    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    @Override
    public void revert(Board board) {
        super.revert(board);

        board.popPiece(to);
        board.putPiece(over, capturedPiece);
        board.putPiece(from, unpromotedPiece);
    }

    @Override
    public Move finalizeMove(Board board, MoveHistory history, FinalizationArguments arguments) {
        if (!arguments.isSwitchSide()) {
            promotionMove = arguments.isPromote();
            return this;
        }

        return new NonImmediateFinalCaptureMove(from, over, to, history, arguments.isPromote());
    }


}
