package lt.vilniustech.moves;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Direction;
import lt.vilniustech.Piece;
import lt.vilniustech.moves.finalization.EmptyFinalizationArguments;

public class ImmediateCaptureMove extends CaptureMove<ImmediateCaptureMove, EmptyFinalizationArguments> {

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
    public ImmediateCaptureMove finalize(Board board, EmptyFinalizationArguments argumentType) {
        return this;
    }

    @Override
    public ImmediateCaptureMove finalize(Board board) {
        return finalize(board, new EmptyFinalizationArguments());
    }

    private Piece capturedPiece;
}
