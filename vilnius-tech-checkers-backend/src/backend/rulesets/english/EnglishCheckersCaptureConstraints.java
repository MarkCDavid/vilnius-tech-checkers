package backend.rulesets.english;

import backend.Board;
import backend.manager.MoveHistory;
import backend.moves.base.Move;
import backend.rulesets.CaptureConstraints;
import backend.rulesets.Filters;

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
