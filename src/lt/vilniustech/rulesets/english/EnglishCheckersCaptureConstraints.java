package lt.vilniustech.rulesets.english;

import lt.vilniustech.Board;
import lt.vilniustech.manager.MoveHistory;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.Filters;

import java.util.List;
import java.util.stream.Collectors;

public class EnglishCheckersCaptureConstraints implements CaptureConstraints {

    public EnglishCheckersCaptureConstraints(Board board, MoveHistory history, Move move) {
        this.move = move;
    }

    @Override
    public List<Move> filterMoves(List<Move> availableMoves) {
        if(!multiCapture)
            return availableMoves;

        return availableMoves.stream()
                .filter(Filters.captureMoves())
                .filter(Filters.movesFromDestination(move))
                .collect(Collectors.toList());
    }

    @Override
    public void setMultiCapture(boolean multiCapture) {
        this.multiCapture = multiCapture;
    }

    private final Move move;
    private boolean multiCapture;
}
