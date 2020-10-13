package lt.vilniustech.rulesets;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Piece;
import lt.vilniustech.moves.Move;
import lt.vilniustech.side.*;

import java.util.List;

public interface CheckersRuleset {

    int getBoardSize();
    boolean isCaptureImmediate();
    boolean isPromotionImmediate();
    boolean canJumpAnywhereBeyond(Piece piece);
    Side processWinningConditions(Side currentSide, List<Move> moves, List<Piece> whitePieces, List<Piece> blackPieces);
    Side getFirstToMove();

    CaptureConstraints getCaptureConstraints(Board board, Move move);

    boolean isKingRow(Side side, Coordinate coordinate);
    PieceSetter getCellFill(Side side);
    Piece createPiece(Side side);
    Piece createKing(Side side);

    boolean isBlackKingRow(Coordinate coordinate);
    PieceSetter getBlackCellFill();
    Piece createBlackPiece();
    Piece createBlackKing();

    boolean isWhiteKingRow(Coordinate coordinate);
    PieceSetter getWhiteCellFill();
    Piece createWhitePiece();
    Piece createWhiteKing();
}
