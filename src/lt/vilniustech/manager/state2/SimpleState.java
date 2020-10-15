package lt.vilniustech.manager.state2;

import lt.vilniustech.Board;
import lt.vilniustech.moves.ImmediateCaptureMove;
import lt.vilniustech.moves.NonImmediateCaptureMove;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.side.Side;

import java.util.List;


public class SimpleState extends AbstractState {

    public SimpleState(Board board, CheckersRuleset ruleset, Side currentSide) {
        this(board, ruleset, currentSide, null);
    }

    public SimpleState(Board board, CheckersRuleset ruleset, Side currentSide, Move processedMove) {
        super(board, ruleset, currentSide);
        this.availableMoves = buildAvailableMoves(processedMove);
    }

    @Override
    public AbstractState process(Move processedMove) {
        if (processedMove.isCapture()) {
            if (ruleset.isCaptureImmediate()) {
                return new ImmediateCaptureState(board, ruleset, currentSide).process((ImmediateCaptureMove) processedMove);
            }
            else{
                return new NonImmediateCaptureState(board, ruleset, currentSide).process((NonImmediateCaptureMove) processedMove);
            }
        }

        if (currentSide.isKingRow(processedMove.getTo())) {
            promote(processedMove);
        }

        currentSide = currentSide.getNext();
        this.availableMoves = buildAvailableMoves(processedMove);
        this.processedMove = processedMove.finalizeMove(board);
        return this;
    }

    private List<Move> buildAvailableMoves(Move processedMove) {
        if(processedMove == null)
            return availableMovesBuilder.buildAvailableMoves(currentSide);

        return ruleset.getCaptureConstraints(board, processedMove)
                .filterMoves(availableMovesBuilder.buildAvailableMoves(currentSide));
    }
}
