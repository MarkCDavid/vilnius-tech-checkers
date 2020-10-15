package lt.vilniustech.moves.finalization;

import lt.vilniustech.Board;
import lt.vilniustech.Piece;
import lt.vilniustech.manager.AvailableMovesBuilder;
import lt.vilniustech.manager.MoveHistorySupport;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.side.Side;

public class FinalizationArguments {


    public static FinalizationArguments build(Board board, CheckersRuleset ruleset, MoveHistorySupport history, Side side, Move move) {
        move.apply(board);
        history.getMoveHistory().add(move);
        try {

            return move.isCapture() ? buildCaptureArguments(board, ruleset, history, side, move) : buildNonCaptureArguments(board, ruleset, history, side, move);
        }
        finally {
            move.revert(board);
            history.getMoveHistory().remove(move);
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

                FinalizationArguments arguments = new FinalizationArguments();
                arguments.promote = true;
                arguments.switchSide = new AvailableMovesBuilder(board, history, ruleset).buildAvailableMoves(promotedPeice).stream().noneMatch(Move::isCapture);
                return arguments;
            }
            finally{
                board.putPiece(move.getTo(), unpromotedPiece);
            }
        }
        FinalizationArguments arguments = new FinalizationArguments();
        arguments.promote = false;
        arguments.switchSide = new AvailableMovesBuilder(board, history, ruleset).buildAvailableMoves(board.getPiece(move.getTo())).stream().noneMatch(Move::isCapture);
        return arguments;
    }





    private FinalizationArguments() { }

    public boolean isPromote() {
        return promote;
    }

    private boolean promote;

    public boolean isSwitchSide() {
        return switchSide;
    }

    private boolean switchSide;
}
