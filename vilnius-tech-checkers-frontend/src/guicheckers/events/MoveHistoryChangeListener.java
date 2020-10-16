package guicheckers.events;

import backend.moves.base.Move;

public interface MoveHistoryChangeListener {
    void onSelectedChange(Move move);
}
