package lt.vilniustech.side;

import lt.vilniustech.Piece;

public interface PieceFactory {
    Piece producePiece();
    boolean ourProduct(Piece piece);
}
