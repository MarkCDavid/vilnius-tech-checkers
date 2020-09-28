package lt.vilniustech.manager.state;

import lt.vilniustech.Board;
import lt.vilniustech.Side;
import lt.vilniustech.manager.MoveCollectionsBuilder;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.moves.Move;
import lt.vilniustech.moves.SimpleMove;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.Filters;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleState extends State {

    @Override
    public State performMove(Board board, Move move) {
        Side side = getSide(board, move);
        board.applyMove(move);

        if(move instanceof SimpleMove) return this;
        if(move instanceof CaptureMove) {
            ArrayList<CaptureMove> unavailable = new ArrayList<>();
            unavailable.add((CaptureMove) move);
            return hasCaptureMoves(board, move, side, unavailable) ? new CaptureState(board, (CaptureMove) move) : this;
        }

        throw new IllegalStateException();
    }

    @Override
    public Side getNextSide(Side currentSide) {
        return Side.opposite(currentSide);
    }

    @Override
    public boolean isMultiCapture() {
        return false;
    }


}
