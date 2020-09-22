package lt.vilniustech.rulesets;

import lt.vilniustech.Coordinate;
import lt.vilniustech.Piece;

public interface CheckersRuleset {

    int getBoardSize();


    boolean isBlackKingRow(Coordinate coordinate);
    CellFill getBlackCellFill();
    Piece createBlackPiece();
    Piece createBlackKing();

    boolean isWhiteKingRow(Coordinate coordinate);
    CellFill getWhiteCellFill();
    Piece createWhitePiece();
    Piece createWhiteKing();
}
