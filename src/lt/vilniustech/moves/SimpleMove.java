package lt.vilniustech.moves;

import lt.vilniustech.*;
import lt.vilniustech.moves.base.AbstractMove;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.moves.finalization.EmptyFinalizationArguments;
import lt.vilniustech.moves.finalization.Finajv;
import lt.vilniustech.moves.finalization.FinalizationArguments;

public class SimpleMove extends AbstractMove<SimpleMove, EmptyFinalizationArguments> {

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
    public Move finalizeMove(Board board, FinalizationArguments argumentType) {
        return null;
    }

    @Override
    public Move finalizeMove(Board board, SimpleMove argumentType) {
        return null;
    }

    @Override
    public SimpleMove finalize(Board board, EmptyFinalizationArguments argumentType) {
        return this;
    }

    @Override
    public SimpleMove finalizeMove(Board board) {
        return finalize(board, new EmptyFinalizationArguments());
    }

    @Override
    public Move finalizeMove(Board board, Finajv finajv) {
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", from, to);
    }
}
