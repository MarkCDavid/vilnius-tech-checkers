package lt.vilniustech.rulesets.english;

import lt.vilniustech.Side;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.CaptureConstraints;

import java.util.List;
import java.util.stream.Collectors;

public class EnglishCheckersCaptureConstraints implements CaptureConstraints {

    public EnglishCheckersCaptureConstraints(Move move) {
        this.move = move;
    }

    @Override
    public List<Move> filterMoves(List<Move> availableMoves) {
        if(capturesAvailable) {
            return availableMoves.stream()
                    .filter(move -> move instanceof CaptureMove && move.getFrom().equals(this.move.getTo()))
                    .collect(Collectors.toList());
        }
        return availableMoves;
    }

    @Override
    public Side getNextSide(Side side) {
        return capturesAvailable ? side : Side.opposite(side);
    }

    @Override
    public void setCapturesAvailable(boolean capturesAvailable) {
        this.capturesAvailable = capturesAvailable;
    }


    private boolean capturesAvailable;
    private final Move move;
}
