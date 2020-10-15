package lt.vilniustech.moves;

import lt.vilniustech.*;
import lt.vilniustech.manager.MoveHistory;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.moves.finalization.FinalizationArguments;

public class SimpleMove extends Move {

    public SimpleMove(Coordinate from, Coordinate to) {
        super(from, to);
    }

    public SimpleMove(Coordinate from, Direction direction, int moveSize) {
        super(from, from.move(direction, moveSize));
    }

    @Override
    public boolean isCapture() {
        return false;
    }

    @Override
    public boolean hasUncaptured() {
        return false;
    }

    @Override
    public boolean isValid(Board board) {
        if(!super.isValid(board))
            return false;

        if (obstacles(board, from, to))
            return false;

        return true;
    }

    @Override
    public void apply(Board board) {
        if(isApplied()) return;

        applied = true;

        unpromotedPiece = board.popPiece(from);
        board.putPiece(to, promotionMove ? unpromotedPiece.promote() : unpromotedPiece);
    }

    @Override
    public void revert(Board board) {
        if(!isApplied()) return;

        applied = false;

        board.popPiece(to);
        board.putPiece(from, unpromotedPiece);
    }

    @Override
    public Move finalizeMove(Board board, MoveHistory history, FinalizationArguments argumentType) {
        promotionMove = argumentType.isPromote();
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", from, to);
    }
}
