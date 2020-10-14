package lt.vilniustech.side;

import lt.vilniustech.Piece;

public abstract class PieceFactory {

    public abstract Piece producePiece();

    public boolean ourProduct(Piece piece) {
        return piece.getSide() == side;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    protected Side side;

}
