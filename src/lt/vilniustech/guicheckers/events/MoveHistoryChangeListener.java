package lt.vilniustech.guicheckers.events;

import lt.vilniustech.moves.base.AbstractMove;

public interface MoveHistoryChangeListener {
    void onSelectedChange(AbstractMove move);
}
