package lt.vilniustech.manager.state2;

import lt.vilniustech.moves.Move;

import java.util.List;

public interface State {
    State process(List<Move> availableMoves, Move processedMove);
    List<Move> getAvailableMoves();
}
