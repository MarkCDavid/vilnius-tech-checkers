package backend.rulesets;

import backend.moves.base.Move;

import java.util.List;

public interface CaptureConstraints {

    List<Move> filterMoves(List<Move> availableMoves);
    void setMultiCapture(boolean multiCapture);
}
