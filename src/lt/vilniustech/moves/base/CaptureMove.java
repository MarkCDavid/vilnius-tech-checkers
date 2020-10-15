package lt.vilniustech.moves.base;

import lt.vilniustech.*;

public abstract class CaptureMove extends Move {

    public CaptureMove(Coordinate from, Coordinate over, Coordinate to) {
        super(from, to);
        this.over = over;
    }

    public CaptureMove(Coordinate from, Direction direction, int moveSize, int jumpSize) {
        super(from, from.move(direction, moveSize + jumpSize));
        this.over = from.move(direction, moveSize);
    }

    @Override
    public boolean isCapture() {
        return true;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public Coordinate getOver() {
        return over;
    }

    @Override
    public boolean isValid(Board board) {
        if(!super.isValid(board))
            return false;

        boolean validCoordinates = board.validCoordinate(this.over);
        if(!validCoordinates)
            return false;

        Piece overPiece = board.getPiece(this.over);
        boolean validPieces = overPiece != null;
        if(!validPieces)
            return false;


        Piece fromPiece = board.getPiece(this.from);
        boolean jumpOverOpponent = fromPiece.getSide() != overPiece.getSide();
        if(!jumpOverOpponent)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return String.format("%s --%s-> %s", from, over, to);
    }

    protected Piece capturedPiece;
    protected final Coordinate over;

}
