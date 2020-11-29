package guicheckers.events;

import api.dto.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class CellClickSupport {

    public CellClickSupport() {
        cellClickListeners = new ArrayList<>();
    }

    public void addCellClickListener(CellClickListener listener) {
        cellClickListeners.add(listener);
    }

    public void removeCellClickListener(CellClickListener listener) {
        cellClickListeners.remove(listener);
    }

    public void emit(Coordinate coordinate) {
        for(CellClickListener listener: cellClickListeners) {
            listener.onCellClick(coordinate);
        }
    }

    private final List<CellClickListener> cellClickListeners;

}
