package lt.vilniustech;

public interface CheckersRuleset {

    int getBoardSize();
    int getFilledRowCount();


    Piece createWhitePiece();
    Piece createBlackPiece();
    Piece createWhiteKing();
    Piece createBlackKing();
}
