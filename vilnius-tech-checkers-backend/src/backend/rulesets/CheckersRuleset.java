package backend.rulesets;

import backend.Board;
import backend.manager.MoveHistory;
import backend.moves.base.Move;
import backend.moves.factory.MoveFactory;
import backend.side.*;

import java.util.List;

public interface CheckersRuleset {

    int getBoardSize();

    boolean isPromotionImmediate();
    boolean isPromotionHalting();

    Side processWinningConditions(Board board, List<Move> availableMoves, List<Side> playingSides, Side current);

    List<Side> getPlayingSides();
    MoveFactory getMoveFactory();
    CaptureConstraints getCaptureConstraints(Board board, MoveHistory history, Move move);

}
