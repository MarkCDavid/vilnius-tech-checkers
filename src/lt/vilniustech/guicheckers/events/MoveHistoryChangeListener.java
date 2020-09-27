package lt.vilniustech.guicheckers.events;

import lt.vilniustech.moves.Move;

public interface MoveHistoryChangeListener {
    void onSelectedChange(Move move);
}
