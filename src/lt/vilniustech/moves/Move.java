package lt.vilniustech.moves;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;

public interface Move {

    Coordinate getFrom();
    Coordinate getTo();

    boolean isValid(Board board);
    boolean perform(Board board);
}
