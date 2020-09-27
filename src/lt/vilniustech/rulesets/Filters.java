package lt.vilniustech.rulesets;

import lt.vilniustech.Coordinate;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.moves.Move;

import java.util.function.Predicate;

public class Filters {

    public static Predicate<Move> captureMoves() {
        return move -> move instanceof CaptureMove;
    }
    public static Predicate<Move> movesFromDestination(Move currentMove) {
        return move -> move.getFrom().equals(currentMove.getTo());
    }

    public static Predicate<Move> moveFromTo(Coordinate from, Coordinate to) {
        return move -> move.getFrom().equals(from) && move.getTo().equals(to);
    }

}
