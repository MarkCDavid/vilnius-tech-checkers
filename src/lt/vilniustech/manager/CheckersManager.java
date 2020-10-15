package lt.vilniustech.manager;

import lt.vilniustech.Board;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.side.Side;

import java.util.List;

public interface CheckersManager {

    boolean isFinished();
    void processMove(Move move);
    List<Move> getAvailableMoves();
    Board getBoard();
    Side getCurrentSide();
    Side getWinner();
    void setWinner(Side side);
}
