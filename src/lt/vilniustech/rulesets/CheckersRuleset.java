package lt.vilniustech.rulesets;

import lt.vilniustech.Coordinate;
import lt.vilniustech.Piece;
import lt.vilniustech.Side;

public interface CheckersRuleset {

    int getBoardSize();

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
