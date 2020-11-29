package guicheckers.events;


import api.dto.Coordinate;

import java.util.EventListener;

public interface CellClickListener extends EventListener {
    void onCellClick(Coordinate coordinate);
}
