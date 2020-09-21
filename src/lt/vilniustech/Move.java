package lt.vilniustech;

public interface Move {

    Coordinate getFrom();
    Coordinate getTo();

    boolean isValid(Board board);
    void perform(Board board);
}
