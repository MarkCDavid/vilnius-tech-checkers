package lt.vilniustech.rulesets;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Piece;
import lt.vilniustech.Side;
import lt.vilniustech.moves.Move;

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
    CellFill getCellFill(Side side);
    Piece createPiece(Side side);
    Piece createKing(Side side);

    boolean isBlackKingRow(Coordinate coordinate);
    CellFill getBlackCellFill();
    Piece createBlackPiece();
    Piece createBlackKing();

    boolean isWhiteKingRow(Coordinate coordinate);
    CellFill getWhiteCellFill();
    Piece createWhitePiece();
    Piece createWhiteKing();
}
