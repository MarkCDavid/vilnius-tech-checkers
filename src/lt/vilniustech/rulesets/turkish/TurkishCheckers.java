package lt.vilniustech.rulesets.turkish;

import lt.vilniustech.*;
import lt.vilniustech.moves.MoveHistory;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.moves.factory.ImmediateMoveFactory;
import lt.vilniustech.moves.factory.MoveFactory;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.side.Side;

import java.util.ArrayList;
import java.util.List;

public class TurkishCheckers implements CheckersRuleset {

    @Override
    public String toString() {
        return "Turkish Checkers";
    }

    @Override
    public int getBoardSize() {
        return 8;
    }

    @Override
    public MoveFactory getMoveFactory() {
        return new ImmediateMoveFactory();
    }

    @Override
    public boolean isPromotionImmediate() {
        return true;
    }

    @Override
    public boolean isPromotionHalting() {
        return true;
    }

    @Override
    public boolean canJumpOverPieceOnlyOnce() {
        return true;
    }

    @Override
    public boolean canJumpAnywhereBeyond(Piece piece) {
        return false;
    }


    @Override
    public Side processWinningConditions(Board board, List<Move> availableMoves, List<Side> playingSides, Side current) {

        List<Piece> pieces0 = playingSides.get(0).getPieces(board);
        List<Piece> pieces1 = playingSides.get(1).getPieces(board);

        if(pieces0.isEmpty()) return playingSides.get(1);
        if(pieces1.isEmpty()) return playingSides.get(0);

        if(pieces0.size() == 1 && pieces1.size() == 1) {
            if(pieces0.get(0).isKing() && !pieces1.get(0).isKing()) return playingSides.get(0);
            if(pieces1.get(0).isKing() && !pieces0.get(0).isKing()) return playingSides.get(1);
        }

        if(availableMoves.isEmpty())
            return new Side("DRAW", null, null, null);
        return null;
    }

    @Override
    public List<Side> getPlayingSides() {
        List<Side> sides = new ArrayList<>();

        Side whiteSide = new Side(
                "White",
                new TurkishCheckersPieceSetter(getBoardSize() - 3, getBoardSize() - 2),
                new TurkishCheckersKingRow(0),
                new TurkishCheckersWhitePieceFactory(getBoardSize())
        );

        Side blackSide = new Side(
                "Black",
                new TurkishCheckersPieceSetter(1, 2),
                new TurkishCheckersKingRow(getBoardSize() - 1),
                new TurkishCheckersBlackPieceFactory(getBoardSize())
        );

        whiteSide.setNext(blackSide);
        blackSide.setNext(whiteSide);

        sides.add(whiteSide);
        sides.add(blackSide);

        return sides;
    }


    @Override
    public CaptureConstraints getCaptureConstraints(Board board, MoveHistory history, Move move) {
        return new TurkishCheckersCaptureConstraints(board, this, history, move);
    }
}
