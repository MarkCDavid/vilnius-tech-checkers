package guicheckers.events;

import backend.Coordinate;

import java.util.EventListener;

public interface CellClickListener extends EventListener {
    void onCellClick(Coordinate coordinate);
}
