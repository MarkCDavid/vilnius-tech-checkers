package backend.moves.factory;

import backend.Coordinate;
import backend.Direction;
import backend.moves.base.CaptureMove;
import backend.moves.base.Move;

public interface MoveFactory {

    Move createMove(Coordinate from, Direction direction, int moveSize);
    CaptureMove createCaptureMove(Coordinate from, Direction direction, int moveSize, int jumpSize);
}
