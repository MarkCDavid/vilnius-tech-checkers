package lt.vilniustech.rulesets;

import lt.vilniustech.Board;
import lt.vilniustech.manager.MoveHistory;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.moves.factory.MoveFactory;
import lt.vilniustech.side.*;

import java.util.List;

public interface CheckersRuleset {

    int getBoardSize();

    boolean isPromotionImmediate();
    boolean isPromotionHalting();

    String processWinningConditions(Board board, List<Move> availableMoves, List<Side> playingSides, Side current);

    List<Side> getPlayingSides();
    MoveFactory getMoveFactory();
    CaptureConstraints getCaptureConstraints(Board board, MoveHistory history, Move move);

}
