package lt.vilniustech.moves;

import lt.vilniustech.*;

public class CaptureMove implements Move {

    public CaptureMove(Coordinate from, Coordinate over, Coordinate to) {
        this.from = from;
        this.over = over;
        this.to = to;
    }

    public CaptureMove(Coordinate from, Direction direction, int moveSize) {
        this.from = from;
        this.to = from.move(direction, moveSize);
        this.over = from.move(direction, moveSize - 1);
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

    public Coordinate getOver() {
        return over;
    }

    @Override
    public boolean isValid(Board board) {
        Cell fromCell = board.getCell(this.from);
        Cell overCell = board.getCell(this.over);
        Cell toCell = board.getCell(this.to);
        if(fromCell == null || toCell == null || overCell == null) return false;

        Piece fromPiece = fromCell.getPiece();
        Piece overPiece = overCell.getPiece();
        Piece toPiece = toCell.getPiece();
        return fromPiece != null && overPiece != null && fromPiece.getSide() != overPiece.getSide() && toPiece == null;
    }

    @Override
    public void apply(Board board) {
        if(isApplied()) return;

        Cell from = board.getCell(this.from);
        Cell over = board.getCell(this.over);
        Cell to = board.getCell(this.to);

        if(from == null || over == null || to == null) return;
        if(over.getPiece() == null) return;

        applied = true;

        Side side = from.getPiece().getSide();
        capturedPiece = over.popPiece();
        to.setPiece(from.popPiece());
    }

    @Override
    public void revert(Board board) {
        if(!isApplied()) return;

        Cell from = board.getCell(this.from);
        Cell over = board.getCell(this.over);
        Cell to = board.getCell(this.to);

        if(from == null || over == null || to == null) return;
        if(over.getPiece() != null) return;

        applied = false;

        over.setPiece(capturedPiece);
        from.setPiece(to.popPiece());

        return;
    }

    @Override
    public String toString() {
        return String.format("%s --%s-> %s", from, over, to);
    }

    private boolean applied;
    private Piece capturedPiece;

    private final Coordinate from;
    private final Coordinate over;
    private final Coordinate to;
}
