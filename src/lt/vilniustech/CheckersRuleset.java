package lt.vilniustech;

public interface CheckersRuleset {

    int getBoardSize();

    CellFill getBlackCellFill();
    Piece createBlackPiece();
    Piece createBlackKing();

    CellFill getWhiteCellFill();
    Piece createWhitePiece();
    Piece createWhiteKing();
}
