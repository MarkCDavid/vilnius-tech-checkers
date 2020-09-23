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
        Cell fromCell = board.getCell(this.from);
        Cell toCell = board.getCell(this.to);
        if(fromCell == null || toCell == null) return false;

        Piece fromPiece = fromCell.getPiece();
        Piece toPiece = toCell.getPiece();
        return fromPiece != null && toPiece == null;
    }

    @Override
    public boolean apply(Board board) {
        if(isApplied()) return false;

        Cell from = board.getCell(this.from);
        Cell to = board.getCell(this.to);
        if(from == null || to == null) return false;

        applied = true;
        to.setPiece(from.popPiece());
        return false;
    }

    @Override
    public boolean revert(Board board) {
        if(!isApplied()) return false;

        Cell from = board.getCell(this.from);
        Cell to = board.getCell(this.to);
        if(from == null || to == null) return false;

        applied = false;
        from.setPiece(to.popPiece());
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", from, to);
    }

    private boolean applied;

    private final Coordinate from;
    private final Coordinate to;
}
