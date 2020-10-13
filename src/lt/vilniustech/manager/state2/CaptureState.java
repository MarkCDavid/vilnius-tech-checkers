package lt.vilniustech.manager.state2;

import lt.vilniustech.Board;
import lt.vilniustech.manager.AvailableMovesBuilder;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.moves.Move;
import lt.vilniustech.moves.NonImmediateCaptureMove;
import lt.vilniustech.moves.NonImmediateFinalCaptureMove;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.side.Side;

import java.util.ArrayList;
import java.util.List;

public class CaptureState extends State {

    public CaptureState(Board board, CheckersRuleset ruleset, Side currentSide) {
        this(board, ruleset, currentSide, null);
    }

    public CaptureState(Board board, CheckersRuleset ruleset, Side currentSide, Move processedMove) {
        super(board, ruleset, currentSide);
        this.availableMoves = buildAvailableMoves(processedMove);
        this.captureMoves = new ArrayList<>();
    }


    @Override
    public State process(Move processedMove) {

        if(processedMove instanceof CaptureMove)
            captureMoves.add((CaptureMove)processedMove);

        if (ruleset.isPromotionImmediate() && currentSide.isKingRow(processedMove.getTo())) {
            promote(processedMove);
            return toSimpleState(processedMove);
        }

        this.availableMoves = buildAvailableMoves(processedMove);

        if(this.availableMoves.isEmpty() && currentSide.isKingRow(processedMove.getTo())) {
            promote(processedMove);
            return toSimpleState(processedMove);
        }

        if(availableMoves.stream().noneMatch(move -> move instanceof CaptureMove)) {
            return toSimpleState(processedMove);
        }

        this.finalizedMove = processedMove;
        return this;
    }

    private State toSimpleState(Move processedMove) {
        Move finalized = processedMove;

        if(processedMove instanceof NonImmediateCaptureMove)
            finalized = ((NonImmediateCaptureMove) processedMove).finalize(board, captureMoves);

        State nextState = new SimpleState(board, ruleset, currentSide.getNext());
        nextState.finalizedMove = finalized;
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
