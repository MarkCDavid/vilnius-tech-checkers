package lt.vilniustech.rulesets;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Piece;
import lt.vilniustech.moves.Move;
import lt.vilniustech.side.*;

import java.util.List;

public interface
CheckersRuleset {

    int getBoardSize();
    boolean isCaptureImmediate();
    boolean isPromotionImmediate();
    boolean canJumpAnywhereBeyond(Piece piece);

    List<Side> getPlayingSides();
    Side processWinningConditions(Board board, List<Move> availableMoves, List<Side> playingSides, Side current);

    CaptureConstraints getCaptureConstraints(Board board, Move move);
}
