package lt.vilniustech.moves;

import lt.vilniustech.*;

public class CaptureMove implements Move {

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
    public void apply(Board board) {
        if(isApplied()) return;
        Piece overPiece = board.getPiece(this.over);

        if(overPiece == null) return;

        takeCapturedPiece(board);
        board.putPiece(to, board.popPiece(from));
    }

    @Override
    public void revert(Board board) {
        if(!isApplied()) return;

        Piece overPiece = board.getPiece(this.over);
        if(overPiece != null) return;

        applied = false;

        setCapturedPiece(board);
        board.putPiece(from, board.popPiece(to));
    }

    public void takeCapturedPiece(Board board) {
        capturedPiece = board.popPiece(over);
    }

    public void setCapturedPiece(Board board) {
        board.putPiece(over, capturedPiece);
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
