package lt.vilniustech.rulesets;

import lt.vilniustech.Coordinate;
import lt.vilniustech.moves.base.AbstractCaptureMove;
import lt.vilniustech.moves.base.AbstractMove;

import java.util.function.Predicate;

public class Filters {

    public static Predicate<AbstractMove> captureMoves() {
        return move -> move instanceof AbstractCaptureMove;
    }
    public static Predicate<AbstractMove> movesFromDestination(AbstractMove currentMove) {
        return move -> move.getFrom().equals(currentMove.getTo());
    }

    public static Predicate<AbstractMove> moveFromTo(Coordinate from, Coordinate to) {
        return move -> move.getFrom().equals(from) && move.getTo().equals(to);
    }

}
