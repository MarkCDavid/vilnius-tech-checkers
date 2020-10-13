package lt.vilniustech.manager.state2;

import lt.vilniustech.Board;
import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.side.Side;

import java.util.List;


public class SimpleState extends State {

    public SimpleState(Board board, CheckersRuleset ruleset, Side currentSide) {
        this(board, ruleset, currentSide, null);
    }

    public SimpleState(Board board, CheckersRuleset ruleset, Side currentSide, Move processedMove) {
        super(board, ruleset, currentSide);
        this.availableMoves = buildAvailableMoves(processedMove);
    }

    @Override
    public State process(Move processedMove) {
        if(processedMove.isCapture())
            return new CaptureState(board, ruleset, currentSide).process(processedMove);

        if (currentSide.isKingRow(processedMove.getTo())) {
            promote(processedMove);
        }

        currentSide = currentSide.getNext();
        this.availableMoves = buildAvailableMoves(processedMove);
        this.finalizedMove = processedMove;
        return this;
    }

    private List<Move> buildAvailableMoves(Move processedMove) {
        if(processedMove == null)
            return availableMovesBuilder.buildAvailableMoves(currentSide);

        return ruleset.getCaptureConstraints(board, processedMove)
                .filterMoves(availableMovesBuilder.buildAvailableMoves(currentSide));
    }
}
