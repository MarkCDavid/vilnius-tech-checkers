package lt.vilniustech.manager.state2;

import lt.vilniustech.Board;
import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.CheckersRuleset;

import java.util.List;

public class SimpleState implements State {

    public SimpleState(Board board, CheckersRuleset ruleset, List<Move> availableMoves) {
        this.board = board;
        this.ruleset = ruleset;
        this.availableMoves = availableMoves;
    }

    @Override
    public State process(List<Move> availableMoves, Move processedMove) {
        this.availableMoves = availableMoves;
        return this;
    }

    @Override
    public List<Move> getAvailableMoves() {
        return availableMoves;
    }

    private List<Move> availableMoves;
    private final Board board;
    private final CheckersRuleset ruleset;
}
