package lt.vilniustech.guicheckers.events;

import lt.vilniustech.moves.base.Move;

public interface MoveHistoryChangeListener {
    void onSelectedChange(Move move);
}
