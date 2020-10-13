package lt.vilniustech.side;

import lt.vilniustech.Piece;

public abstract class PieceFactory {

    public abstract Piece producePiece(Side side);

    public boolean ourProduct(Side side, Piece piece) {
        return piece.getSide() == side;
    }

}
