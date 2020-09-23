package lt.vilniustech.moves;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;

public interface Move {

    Coordinate getFrom();
    Coordinate getTo();

    boolean isApplied();
    boolean isValid(Board board);
    boolean apply(Board board);
    boolean revert(Board board);
}
