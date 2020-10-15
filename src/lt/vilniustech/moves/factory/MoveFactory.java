package lt.vilniustech.moves.factory;

import lt.vilniustech.Coordinate;
import lt.vilniustech.Direction;
import lt.vilniustech.moves.base.CaptureMove;
import lt.vilniustech.moves.base.Move;

public interface MoveFactory {

    Move createMove(Coordinate from, Direction direction, int moveSize);
    CaptureMove createCaptureMove(Coordinate from, Direction direction, int moveSize, int jumpSize);
}
