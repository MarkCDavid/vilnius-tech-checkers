package backend.moves.factory;

import backend.Coordinate;
import backend.Direction;
import backend.moves.ImmediateCaptureMove;
import backend.moves.SimpleMove;
import backend.moves.base.CaptureMove;
import backend.moves.base.Move;

public class ImmediateMoveFactory implements MoveFactory {
    @Override
    public Move createMove(Coordinate from, Direction direction, int moveSize) {
        return new SimpleMove(from, direction, moveSize);
    }

    @Override
    public CaptureMove createCaptureMove(Coordinate from, Direction direction, int moveSize, int jumpSize) {
        return new ImmediateCaptureMove(from, direction, moveSize, jumpSize);
    }
}
