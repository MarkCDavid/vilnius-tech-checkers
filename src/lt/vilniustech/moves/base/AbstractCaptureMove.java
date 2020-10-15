package lt.vilniustech.moves.base;

import lt.vilniustech.*;
import lt.vilniustech.moves.finalization.FinalizationArguments;

public abstract class AbstractCaptureMove<F extends Move, A extends FinalizationArguments> extends AbstractMove<F, A> implements CaptureMove {

    public AbstractCaptureMove(Coordinate from, Coordinate over, Coordinate to) {
        super(from, to);
        this.over = over;
    }

    public AbstractCaptureMove(Coordinate from, Direction direction, int moveSize, int jumpSize) {
        super(from, from.move(direction, moveSize + jumpSize));
        this.over = from.move(direction, moveSize);
    }

    @Override
    public boolean isCapture() {
        return true;
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

    protected final Coordinate over;
}
