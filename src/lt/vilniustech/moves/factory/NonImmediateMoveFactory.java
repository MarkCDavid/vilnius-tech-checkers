package lt.vilniustech.moves.factory;

import lt.vilniustech.Coordinate;
import lt.vilniustech.Direction;
import lt.vilniustech.moves.NonImmediateCaptureMove;
import lt.vilniustech.moves.SimpleMove;
import lt.vilniustech.moves.base.CaptureMove;
import lt.vilniustech.moves.base.Move;

public class NonImmediateMoveFactory implements MoveFactory {
    @Override
    public Move createMove(Coordinate from, Direction direction, int moveSize) {
        return new SimpleMove(from, direction, moveSize);
    }

    @Override
    public CaptureMove createCaptureMove(Coordinate from, Direction direction, int moveSize, int jumpSize) {
        return new NonImmediateCaptureMove(from, direction, moveSize, jumpSize);
    }
}
