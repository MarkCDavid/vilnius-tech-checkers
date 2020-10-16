package backend.moves.base;

import backend.Board;
import backend.Coordinate;
import backend.Direction;
import backend.Piece;

public abstract class CaptureMove extends Move {

    public CaptureMove(Coordinate from, Coordinate over, Coordinate to) {
        super(from, to);
        this.over = over;
    }

    public CaptureMove(Coordinate from, Direction direction, int moveSize, int jumpSize) {
        super(from, from.move(direction, moveSize + jumpSize));
        this.over = from.move(direction, moveSize);
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

        if (obstacles(board, from, over) || obstacles(board, over, to))
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
    public void apply(Board board) {
        if(isApplied())
            return;

        Piece overPiece = board.getPiece(this.over);
        if(overPiece == null)
            return;

        applied = true;
    }

    @Override
    public void revert(Board board) {
        if(!isApplied())
            return;

        Piece overPiece = board.getPiece(this.over);
        if(overPiece != null)
            return;

        applied = false;
    }

    @Override
    public String toString() {
        return String.format("%s --%s-> %s", from, over, to);
    }

    protected Piece capturedPiece;
    protected final Coordinate over;

}
