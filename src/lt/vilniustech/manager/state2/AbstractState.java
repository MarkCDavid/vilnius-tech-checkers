package lt.vilniustech.manager.state2;

import lt.vilniustech.Board;
import lt.vilniustech.manager.AvailableMovesBuilder;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.side.Side;

import java.util.List;

public abstract class AbstractState<T extends Move> {

    protected AbstractState(Board board, CheckersRuleset ruleset, Side currentSide) {
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

    public Move getProcessedMove() {
        return processedMove;
    }

    protected void promote(T processedMove) {
        board.putPiece(processedMove.getTo(), board.popPiece(processedMove.getTo()).promote());
    }

    public AbstractState process(T processedMove) {



        AbstractState state = processCore(processedMove);

        boolean stateSwitch = state != this;



        if(stateSwitch) {

        }
        else {

        }

        return state;
    }

    public abstract AbstractState processCore(T processedMove);

    protected AbstractState switchTo(AbstractState state, T processedMove) {
        return state.process(processedMove);
    }

    protected List<Move> availableMoves;

    protected Move processedMove;
    protected Side currentSide;
    protected final Board board;
    protected final CheckersRuleset ruleset;
    protected final AvailableMovesBuilder availableMovesBuilder;
}
