package lt.vilniustech.rulesets;

import lt.vilniustech.moves.Move;
import lt.vilniustech.side.Side;

import java.util.List;

public interface CaptureConstraints {

    List<Move> filterMoves(List<Move> availableMoves);
    Side getNextSide(Side side);
    void setMultipleCaptures(boolean multipleCaptures);
}
