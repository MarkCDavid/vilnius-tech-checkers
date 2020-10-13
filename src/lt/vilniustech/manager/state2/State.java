package lt.vilniustech.manager.state2;

import lt.vilniustech.Board;
import lt.vilniustech.manager.AvailableMovesBuilder;
import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.side.Side;

import java.util.List;

public abstract class State {

    protected State(Board board, CheckersRuleset ruleset, Side currentSide) {
        this.board = board;
        this.ruleset = ruleset;
        this.availableMovesBuilder = new AvailableMovesBuilder(board, ruleset);
        this.currentSide = currentSide;
    }

    public List<Move> getAvailableMoves() {
        return availableMoves;
    }

    public Side getCurrentSide() {
        return currentSide;
    }

    public Move getFinalizedMove() {
        return finalizedMove;
    }

    protected void promote(Move processedMove) {
        board.putPiece(processedMove.getTo(), board.popPiece(processedMove.getTo()).promote());
    }



    public abstract State process(Move processedMove);

    protected List<Move> availableMoves;

    protected Move finalizedMove;
    protected Side currentSide;
    protected final Board board;
    protected final CheckersRuleset ruleset;
    protected final AvailableMovesBuilder availableMovesBuilder;
}
