package lt.vilniustech.moves.finalization;

import lt.vilniustech.Board;
import lt.vilniustech.manager.AvailableMovesBuilder;
import lt.vilniustech.manager.MoveHistorySupport;
import lt.vilniustech.moves.base.CaptureMove;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.side.Side;
import lt.vilniustech.utils.TemporaryAddHistory;
import lt.vilniustech.utils.TemporaryMove;
import lt.vilniustech.utils.TemporaryPromotion;

public class FinalizationArgumentsBuilder {

    public FinalizationArgumentsBuilder(Board board, CheckersRuleset ruleset, MoveHistorySupport history) {
        this.board = board;
        this.ruleset = ruleset;
        this.history = history;
        this.movesBuilder = new AvailableMovesBuilder(board, ruleset, history);
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
        return constraints.filterMoves(movesBuilder.buildAvailableMoves(board.getPiece(move.getTo()))).isEmpty();
    }

    private final Board board;
    private final CheckersRuleset ruleset;
    private final MoveHistorySupport history;
    private final AvailableMovesBuilder movesBuilder;
}
