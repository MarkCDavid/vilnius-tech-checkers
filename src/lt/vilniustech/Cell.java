package lt.vilniustech;

public class Cell {

    public Piece getPiece() {
        return piece;
    }

    public Piece popPiece() {
        Piece piece = this.piece;
        this.piece = null;
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    private Piece piece;


}
