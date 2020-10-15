package lt.vilniustech.manager;

import lt.vilniustech.moves.base.Move;

import java.util.List;

public interface MoveHistorySupport {
    List<Move> getMoveHistory();
}
