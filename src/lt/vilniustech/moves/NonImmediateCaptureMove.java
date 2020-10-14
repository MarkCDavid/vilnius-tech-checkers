package lt.vilniustech.moves;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Direction;
import lt.vilniustech.Piece;
import lt.vilniustech.moves.finalization.NonImmediateFinalizationArguments;

import java.util.ArrayList;
import java.util.List;

public class NonImmediateCaptureMove extends CaptureMove<NonImmediateFinalCaptureMove, NonImmediateFinalizationArguments> {

    public NonImmediateCaptureMove(Coordinate from, Coordinate over, Coordinate to) {
        super(from, over, to);
    }

    public NonImmediateCaptureMove(Coordinate from, Direction direction, int moveSize, int jumpSize) {
        super(from, direction, moveSize, jumpSize);
    }

    @Override
    public void apply(Board board) {
        if(isApplied()) return;

        applied = true;

        board.movePiece(from, to);
    }

    @Override
    public void revert(Board board) {
        if(!isApplied()) return;

        applied = false;

        board.movePiece(to, from);
    }

    @Override
    public NonImmediateFinalCaptureMove finalize(Board board, NonImmediateFinalizationArguments arguments) {
        NonImmediateFinalCaptureMove finalized = new NonImmediateFinalCaptureMove(from, over, to, arguments.getCaptureMoves());
        finalized.apply(board);
        return finalized;
    }

    @Override
    public NonImmediateFinalCaptureMove finalize(Board board) {
        return finalize(board, new NonImmediateFinalizationArguments(new ArrayList<>()));
    }
}
