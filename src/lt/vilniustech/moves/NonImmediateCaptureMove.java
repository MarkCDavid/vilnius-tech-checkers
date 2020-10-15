package lt.vilniustech.moves;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Direction;
import lt.vilniustech.manager.MoveHistorySupport;
import lt.vilniustech.moves.base.CaptureMove;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.moves.finalization.FinalizationArguments;

public class NonImmediateCaptureMove extends CaptureMove {

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
    public Move finalizeMove(Board board, MoveHistorySupport support, FinalizationArguments arguments) {
        if(!arguments.isSwitchSide())
            return this;

        return new NonImmediateFinalCaptureMove(from, over, to, support.getMoveHistory());
    }
}
