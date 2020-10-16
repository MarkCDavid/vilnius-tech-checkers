package backend.rulesets;

import backend.Coordinate;
import backend.moves.base.CaptureMove;
import backend.moves.base.Move;

import java.util.function.Predicate;

public class Filters {

    public static Predicate<Move> captureMoves() {
        return move -> move instanceof CaptureMove;
    }
    public static Predicate<Move> movesFromDestination(Move currentMove) {
        if(currentMove == null) return move -> true;
        return move -> move.getFrom().equals(currentMove.getTo());
    }

    public static Predicate<Move> moveFromTo(Coordinate from, Coordinate to) {
        return move -> move.getFrom().equals(from) && move.getTo().equals(to);
    }

}
