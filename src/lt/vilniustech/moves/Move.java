package lt.vilniustech.moves;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;

public interface Move {

    Coordinate getFrom();
    Coordinate getTo();

    boolean isApplied();
    boolean isValid(Board board);
    void apply(Board board);
    void revert(Board board);
}
