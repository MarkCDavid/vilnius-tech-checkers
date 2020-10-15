package lt.vilniustech.rulesets.english;

import lt.vilniustech.Board;
import lt.vilniustech.moves.base.AbstractMove;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.Filters;
import lt.vilniustech.side.Side;

import java.util.List;
import java.util.stream.Collectors;

public class EnglishCheckersCaptureConstraints implements CaptureConstraints {

    public EnglishCheckersCaptureConstraints(Board board, AbstractMove move) {
        this.move = move;
    }

    @Override
    public List<AbstractMove> filterMoves(List<Move> availableMoves) {
        return availableMoves.stream()
                .filter(Filters.captureMoves())
                .filter(Filters.movesFromDestination(move))
                .collect(Collectors.toList());
    }

    @Override
    public Side getNextSide(Side side) {
        return side;
    }


    private final AbstractMove move;
}
