package api.endpoints;

import api.dto.Cell;
import api.dto.Coordinate;
import api.dto.Move;

import java.util.List;

public interface GameService {

    int getBoardSize();
    List<Cell> getBoard();
    List<Coordinate> getInteractable();
    List<Move> getAvailableMoves(Coordinate from);
}
