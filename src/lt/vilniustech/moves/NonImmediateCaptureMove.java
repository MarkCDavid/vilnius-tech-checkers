package lt.vilniustech.moves;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Direction;
import lt.vilniustech.Piece;

import java.util.ArrayList;
import java.util.List;

public class NonImmediateCaptureMove extends CaptureMove {

    public NonImmediateCaptureMove(Coordinate from, Coordinate over, Coordinate to) {
        super(from, over, to);
    }

    public NonImmediateCaptureMove(Coordinate from, Direction direction, int moveSize, int jumpSize) {
        super(from, direction, moveSize, jumpSize);
    }

    public NonImmediateFinalCaptureMove finalize(Board board, List<CaptureMove> captureMoves) {
        NonImmediateFinalCaptureMove finalized = new NonImmediateFinalCaptureMove(from, over, to, captureMoves);
        finalized.apply(board);
        return finalized;
    }

    @Override
    public void apply(Board board) {
        if(isApplied()) return;

        applied = true;

        board.putPiece(to, board.popPiece(from));
    }

    @Override
    public void revert(Board board) {
        if(!isApplied()) return;

        applied = false;

        board.putPiece(from, board.popPiece(to));
    }
}
