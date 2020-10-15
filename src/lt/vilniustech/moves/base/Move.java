package lt.vilniustech.moves.base;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Piece;
import lt.vilniustech.manager.MoveHistory;
import lt.vilniustech.moves.finalization.FinalizationArguments;
import lt.vilniustech.utils.iterator.DirectionalIterable;

public abstract class Move {

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

    protected boolean obstacles(Board board, Coordinate begin, Coordinate end) {
        for(Coordinate coordinate: new DirectionalIterable(begin, end, false)) {
            if(board.getPiece(coordinate) != null)
                return true;
        }
        return false;
    }

    public abstract boolean hasUncaptured();

    public void apply(Board board) {
        if(isApplied()) return;
        applied = true;
    }
    public void revert(Board board) {
        if(!isApplied()) return;
        applied = false;
    }

    public abstract Move finalizeMove(Board board, MoveHistory history, FinalizationArguments argumentType);

    protected boolean applied;

    protected Piece unpromotedPiece;

    protected void setPromotionMove(boolean promotionMove) {
        this.promotionMove = promotionMove;
    }

    protected boolean promotionMove;

    protected final Coordinate from;
    protected final Coordinate to;
}
