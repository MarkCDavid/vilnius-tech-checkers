package lt.vilniustech.moves;

import lt.vilniustech.moves.base.Move;
import lt.vilniustech.utils.BackwardsIterator;

import java.util.*;

public class MoveHistory {

    public MoveHistory() {
        history = new ArrayList<>();
    }

    public void add(Move move) {
        history.add(move);
    }

    public void remove(Move move) {
        history.remove(move);
    }

    private final List<Move> history;

    public Iterable<Move> forwards() {
        return history::listIterator;
    }

    public Iterable<Move> backwards() {
        return () -> new BackwardsIterator<>(history);
    }
}
