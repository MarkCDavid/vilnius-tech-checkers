package lt.vilniustech.moves;

import lt.vilniustech.*;

public class SimpleMove implements Move {

    public SimpleMove(Coordinate from, Coordinate to) {
        this.from = from;
        this.to = to;
    }

    public SimpleMove(Coordinate from, Direction direction, int moveSize) {
        this.from = from;
        this.to = from.move(direction, moveSize);
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
    public boolean isApplied() {
        return applied;
    }

    @Override
    public boolean isValid(Board board) {
        return board.validCoordinate(this.from) &&
            board.validCoordinate(this.to) &&
            board.getPiece(this.from) != null &&
            board.getPiece(this.to) == null;
    }

    @Override
    public void apply(Board board) {
        if(isApplied()) return;
        applied = true;
        board.putPiece(to, board.popPiece(from));
    }

    @Override
    public void revert(Board board) {
        if(!isApplied()) return;
        applied = false;
        board.putPiece(from, board.popPiece(to));
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", from, to);
    }

    private boolean applied;

    private final Coordinate from;
    private final Coordinate to;
}
