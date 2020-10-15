package lt.vilniustech.guicheckers.events;

import lt.vilniustech.moves.base.AbstractMove;

import java.util.ArrayList;
import java.util.List;

public class MoveHistoryChangeSupport  {

    public MoveHistoryChangeSupport() {
        cellClickListeners = new ArrayList<>();
    }

    public void addHistoryChangeListener(MoveHistoryChangeListener listener) {
        cellClickListeners.add(listener);
    }

    public void removeHistoryChangeListener(MoveHistoryChangeListener listener) {
        cellClickListeners.remove(listener);
    }

    public void emit(AbstractMove move) {
        for(MoveHistoryChangeListener listener: cellClickListeners) {
            listener.onSelectedChange(move);
        }
    }

    private final List<MoveHistoryChangeListener> cellClickListeners;

}