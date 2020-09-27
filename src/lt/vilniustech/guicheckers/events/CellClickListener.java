package lt.vilniustech.guicheckers.events;

import lt.vilniustech.Coordinate;

import java.util.EventListener;

public interface CellClickListener extends EventListener {
    void onCellClick(Coordinate coordinate);
}
