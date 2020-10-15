package lt.vilniustech.manager.state2;

import lt.vilniustech.Board;
import lt.vilniustech.moves.ImmediateCaptureMove;
import lt.vilniustech.moves.base.AbstractCaptureMove;
import lt.vilniustech.moves.base.AbstractMove;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.side.Side;

import java.util.List;

public class ImmediateCaptureState extends AbstractState<ImmediateCaptureMove> {

    public ImmediateCaptureState(Board board, CheckersRuleset ruleset, Side currentSide) {
        this(board, ruleset, currentSide, null);
    }

    public ImmediateCaptureState(Board board, CheckersRuleset ruleset, Side currentSide, AbstractMove processedMove) {
        super(board, ruleset, currentSide);
        this.availableMoves = buildAvailableMoves(processedMove);
    }


    @Override
    public AbstractState process(ImmediateCaptureMove processedMove) {

        if (ruleset.isPromotionImmediate() && currentSide.isKingRow(processedMove.getTo())) {
            promote(processedMove);
            return toSimpleState(processedMove);
        }

        this.availableMoves = buildAvailableMoves(processedMove);

        if(this.availableMoves.isEmpty() && currentSide.isKingRow(processedMove.getTo())) {
            promote(processedMove);
            return toSimpleState(processedMove);
        }

        if(availableMoves.stream().noneMatch(move -> move instanceof AbstractCaptureMove)) {
            return toSimpleState(processedMove);
        }

        this.processedMove = processedMove.finalizeMove(board);
        return this;
    }

    private AbstractState toSimpleState(ImmediateCaptureMove processedMove) {
        AbstractState nextState = new SimpleState(board, ruleset, currentSide.getNext());
        nextState.processedMove = processedMove.finalizeMove(board);
        return nextState;
    }

    private List<Move> buildAvailableMoves(Move processedMove) {
        if(processedMove == null)
            return availableMovesBuilder.buildAvailableMoves(currentSide);

        CaptureConstraints captureConstraints = ruleset.getCaptureConstraints(board, processedMove);
        return captureConstraints.filterMoves(availableMovesBuilder.buildAvailableMoves(currentSide));
    }
}
