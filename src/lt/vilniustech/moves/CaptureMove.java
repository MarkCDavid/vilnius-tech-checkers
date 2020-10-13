package lt.vilniustech.moves;

import lt.vilniustech.*;

public abstract class CaptureMove implements Move {

    public CaptureMove(Coordinate from, Coordinate over, Coordinate to) {
        this.from = from;
        this.over = over;
        this.to = to;
    }

    public CaptureMove(Coordinate from, Direction direction, int moveSize, int jumpSize) {
        this.from = from;
        this.over = from.move(direction, moveSize);
        this.to = from.move(direction, moveSize + jumpSize);
    }

    @Override
    public Coordinate getFrom() {
        return from;
    }

    @Override
    public Coordinate getTo() {
        return to;
    }

    @Override
    public boolean isCapture() {
        return true;
    }

    @Override
    public boolean isApplied() {
        return applied;
    }

    public Coordinate getOver() {
        return over;
    }

    @Override
    public boolean isValid(Board board) {
        Piece fromPiece = board.getPiece(this.from);
        Piece overPiece = board.getPiece(this.over);
        Piece toPiece = board.getPiece(this.to);
        return board.validCoordinate(this.from) &&
                board.validCoordinate(this.over) &&
                board.validCoordinate(this.to) &&
                fromPiece != null && overPiece != null && toPiece == null &&
                fromPiece.getSide() != overPiece.getSide();

    }

    @Override
    public String toString() {
        return String.format("%s --%s-> %s", from, over, to);
    }

    protected boolean applied;

    protected final Coordinate from;
    protected final Coordinate over;
    protected final Coordinate to;
}
