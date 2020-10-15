package lt.vilniustech.manager.state2;

import lt.vilniustech.Board;
import lt.vilniustech.moves.NonImmediateCaptureMove;
import lt.vilniustech.moves.base.AbstractCaptureMove;
import lt.vilniustech.moves.base.CaptureMove;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.moves.finalization.NonImmediateFinalizationArguments;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.side.Side;

import java.util.ArrayList;
import java.util.List;

public class NonImmediateCaptureState extends AbstractState<NonImmediateCaptureMove> {

    public NonImmediateCaptureState(Board board, CheckersRuleset ruleset, Side currentSide) {
        this(board, ruleset, currentSide, null);
    }

    public NonImmediateCaptureState(Board board, CheckersRuleset ruleset, Side currentSide, CaptureMove processedMove) {
        super(board, ruleset, currentSide);
        this.availableMoves = buildAvailableMoves(processedMove);
        this.captureMoves = new ArrayList<>();
    }


    @Override
    public AbstractState process(NonImmediateCaptureMove processedMove) {
        captureMoves.add(processedMove);

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

        this.processedMove = processedMove;
        return this;
    }

    private AbstractState toSimpleState(CaptureMove processedMove) {
        AbstractState nextState = new SimpleState(board, ruleset, currentSide.getNext());
        nextState.processedMove = processedMove.finalizeMove(board, new NonImmediateFinalizationArguments(captureMoves));
        return nextState;
    }

    private List<Move> buildAvailableMoves(Move processedMove) {
        if(processedMove == null)
            return availableMovesBuilder.buildAvailableMoves(currentSide);

        CaptureConstraints captureConstraints = ruleset.getCaptureConstraints(board, processedMove);
        captureConstraints.setMultipleCaptures(true);
        return captureConstraints.filterMoves(availableMovesBuilder.buildAvailableMoves(currentSide));
    }

    private final List<CaptureMove> captureMoves;


}
