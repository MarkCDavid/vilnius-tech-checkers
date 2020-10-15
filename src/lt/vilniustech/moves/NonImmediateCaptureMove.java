package lt.vilniustech.moves;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Direction;
import lt.vilniustech.moves.base.AbstractCaptureMove;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.moves.finalization.Finajv;
import lt.vilniustech.moves.finalization.FinalizationArguments;
import lt.vilniustech.moves.finalization.NonImmediateFinalizationArguments;

import java.util.ArrayList;

public class NonImmediateCaptureMove extends AbstractCaptureMove<NonImmediateFinalCaptureMove, NonImmediateFinalizationArguments> {

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
    public NonImmediateFinalCaptureMove finalizeMove(Board board, FinalizationArguments argumentType) {
        return null;
    }

    public NonImmediateFinalCaptureMove finalizeMove(Board board, NonImmediateFinalizationArguments arguments) {
        NonImmediateFinalCaptureMove finalized = new NonImmediateFinalCaptureMove(from, over, to, arguments.getCaptureMoves());
        finalized.apply(board);
        return finalized;
    }

    @Override
    public Move finalizeMove(Board board, Finajv finajv) {
        return this;
    }

    @Override
    public NonImmediateFinalCaptureMove finalizeMove(Board board) {
        return finalizeMove(board, new NonImmediateFinalizationArguments(new ArrayList<>()));
    }
}
