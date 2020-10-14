package lt.vilniustech.moves;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.moves.finalization.FinalizationArguments;

public abstract class Move<F, A extends FinalizationArguments> {

    public Move(Coordinate from, Coordinate to) {
        this.from = from;
        this.to = to;
    }

    public Coordinate getFrom() {
        return from;
    }

    public Coordinate getTo() {
        return to;
    }

    public boolean isApplied() {
        return applied;
    }

    public boolean isValid(Board board) {
        boolean validCoordinates = board.validCoordinate(this.from) && board.validCoordinate(this.to);
        if(!validCoordinates)
            return false;

        boolean validPieces = board.getPiece(this.from) != null && board.getPiece(this.to) == null;
        if(!validPieces)
            return false;

        return true;
    }

    public abstract boolean isCapture();
    public abstract void apply(Board board);
    public abstract void revert(Board board);

    public abstract F finalize(Board board, A argumentType);
    public abstract F finalize(Board board);

    protected boolean applied;

    protected final Coordinate from;
    protected final Coordinate to;
}
