package lt.vilniustech.moves.base;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.moves.finalization.Finajv;
import lt.vilniustech.moves.finalization.FinalizationArguments;

public interface Move {

    Coordinate getFrom();
    Coordinate getTo();
    boolean isApplied();
    boolean isValid(Board board);

    boolean isCapture();
    void apply(Board board);
    void revert(Board board);

   Move finalizeMove(FinalizationArguments argumentType);

}
