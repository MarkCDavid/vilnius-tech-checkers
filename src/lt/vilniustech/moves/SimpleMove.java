package lt.vilniustech.moves;

import lt.vilniustech.*;
import lt.vilniustech.moves.finalization.EmptyFinalizationArguments;

public class SimpleMove extends Move<SimpleMove, EmptyFinalizationArguments> {

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
    public SimpleMove finalize(Board board, EmptyFinalizationArguments argumentType) {
        return this;
    }

    @Override
    public SimpleMove finalize(Board board) {
        return finalize(board, new EmptyFinalizationArguments());
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", from, to);
    }
}
