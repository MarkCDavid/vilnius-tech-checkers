package lt.vilniustech.moves.finalization;

import lt.vilniustech.Board;
import lt.vilniustech.manager.AvailableMovesBuilder;
import lt.vilniustech.manager.MoveHistory;
import lt.vilniustech.moves.base.CaptureMove;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.side.Side;
import lt.vilniustech.utils.temporary.TemporaryAddHistory;
import lt.vilniustech.utils.temporary.TemporaryMove;
import lt.vilniustech.utils.temporary.TemporaryPromotion;

import java.util.List;

public class FinalizationArgumentsBuilder {

    public FinalizationArgumentsBuilder(Board board, CheckersRuleset ruleset, MoveHistory history, AvailableMovesBuilder availableMovesBuilder) {
        this.board = board;
        this.ruleset = ruleset;
        this.history = history;
        this.movesBuilder = availableMovesBuilder;
    }

    public FinalizationArguments build(Side side, Move move) {

        try (var temporaryMove = new TemporaryMove(board, move);
             var temporaryAddHistory = new TemporaryAddHistory(history, move)
        ) {
            return move instanceof CaptureMove ?
                    buildCaptureArguments(side, move) :
                    buildNonCaptureArguments(side, move);
        }
    }

    private FinalizationArguments buildNonCaptureArguments(Side side, Move move) {
        FinalizationArguments arguments = new FinalizationArguments();

        arguments.setPromote(side.isKingRow(move.getTo()));
        arguments.setSwitchSide(true);

        return arguments;
    }

    private FinalizationArguments buildCaptureArguments(Side side, Move move) {

        boolean kingRow = side.isKingRow(move.getTo());

        if(ruleset.isPromotionHalting() && kingRow) {
            FinalizationArguments arguments = new FinalizationArguments();
            arguments.setPromote(true);
            arguments.setSwitchSide(true);
            return arguments;
        }

        if(ruleset.isPromotionImmediate() && kingRow) {
            try (var temporaryPromotion = new TemporaryPromotion(board, move)){
                FinalizationArguments arguments = new FinalizationArguments();
                arguments.setPromote(true);
                arguments.setSwitchSide(captureMovesUnavailable(move));
                return arguments;
            }
        }

        FinalizationArguments arguments = new FinalizationArguments();
        arguments.setPromote(false);
        arguments.setSwitchSide(captureMovesUnavailable(move));
        return arguments;
    }

    private boolean captureMovesUnavailable(Move move) {
        CaptureConstraints constraints = ruleset.getCaptureConstraints(board, history, move);
        constraints.setMultiCapture(true);
        List<Move> availableMoves = movesBuilder.buildAvailableMoves(board.getPiece(move.getTo()));
        return constraints.filterMoves(availableMoves).stream().noneMatch(m -> m instanceof CaptureMove);
    }

    private final Board board;
    private final CheckersRuleset ruleset;
    private final MoveHistory history;
    private final AvailableMovesBuilder movesBuilder;
}
