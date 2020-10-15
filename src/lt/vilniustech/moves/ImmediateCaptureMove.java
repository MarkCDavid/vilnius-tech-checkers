package lt.vilniustech.moves;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Direction;
import lt.vilniustech.Piece;
import lt.vilniustech.moves.base.AbstractCaptureMove;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.moves.finalization.EmptyFinalizationArguments;
import lt.vilniustech.moves.finalization.Finajv;
import lt.vilniustech.moves.finalization.FinalizationArguments;

public class ImmediateCaptureMove extends AbstractCaptureMove<ImmediateCaptureMove, EmptyFinalizationArguments> {

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
        board.movePiece(from, to);
        board.putPiece(to, board.popPiece(from));
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
        board.movePiece(to, from);
    }

    @Override
    public ImmediateCaptureMove finalizeMove(Board board, FinalizationArguments argumentType) {
        return null;
    }

    public ImmediateCaptureMove finalizeMove(Board board, EmptyFinalizationArguments argumentType) {
        return this;
    }

    @Override
    public ImmediateCaptureMove finalizeMove(Board board) {
        return finalizeMove(board, new EmptyFinalizationArguments());
    }

    @Override
    public Move finalizeMove(Board board, Finajv finajv) {
        return this;
    }

    private Piece capturedPiece;
}
