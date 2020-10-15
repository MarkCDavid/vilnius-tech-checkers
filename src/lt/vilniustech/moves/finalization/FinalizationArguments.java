package lt.vilniustech.moves.finalization;

import lt.vilniustech.Board;
import lt.vilniustech.Piece;
import lt.vilniustech.manager.AvailableMovesBuilder;
import lt.vilniustech.manager.MoveHistorySupport;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.side.Side;

import java.util.List;

public class FinalizationArguments {


    public static FinalizationArguments build(Board board, CheckersRuleset ruleset, MoveHistorySupport history, Side side, Move move) {
        move.apply(board);
        try {
            return move.isCapture() ? buildCaptureArguments(board, ruleset, history, side, move) : buildNonCaptureArguments(board, ruleset, history, side, move);
        }
        finally {
            move.revert(board);
        }
    }

    private static FinalizationArguments buildNonCaptureArguments(Board board, CheckersRuleset ruleset, MoveHistorySupport history, Side side, Move move) {
        FinalizationArguments arguments = new FinalizationArguments();

        arguments.promote = side.isKingRow(move.getTo());
        arguments.switchSide = true;

        return arguments;
    }

    private static FinalizationArguments buildCaptureArguments(Board board, CheckersRuleset ruleset, MoveHistorySupport history, Side side, Move move) {

        if(ruleset.isPromotionHalting() && side.isKingRow(move.getTo())) {
            FinalizationArguments arguments = new FinalizationArguments();
            arguments.promote = true;
            arguments.switchSide = true;
            return arguments;
        }

        if(ruleset.isPromotionImmediate() && side.isKingRow(move.getTo())) {
            Piece unpromotedPiece = board.popPiece(move.getTo());
            Piece promotedPeice = unpromotedPiece.promote();
            board.putPiece(move.getTo(), promotedPeice);

            try {
                List<Move> movesAfterPromotion = new AvailableMovesBuilder(board, history, ruleset).buildAvailableMoves(promotedPeice);
                if(!movesAfterPromotion.isEmpty()) {
                    FinalizationArguments arguments = new FinalizationArguments();
                    arguments.promote = true;
                    arguments.switchSide = false;
                    return arguments;
                }
            }
            finally{
                board.putPiece(move.getTo(), unpromotedPiece);
            }
        }

        CaptureConstraints captureConstraints = ruleset.getCaptureConstraints(board, move);
        List<Move> availableMoves = captureConstraints.filterMoves();








    }





    private FinalizationArguments() { }

    private boolean promote;
    private boolean switchSide;
}
