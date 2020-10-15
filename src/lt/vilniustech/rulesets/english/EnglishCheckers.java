package lt.vilniustech.rulesets.english;

import lt.vilniustech.*;
import lt.vilniustech.manager.MoveHistory;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.moves.factory.MoveFactory;
import lt.vilniustech.moves.factory.NonImmediateMoveFactory;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.side.Side;

import java.util.ArrayList;
import java.util.List;

public class EnglishCheckers implements CheckersRuleset {

    @Override
    public String toString() {
        return "English Checkers";
    }

    @Override
    public int getBoardSize() {
        return 8;
    }

    @Override
    public MoveFactory getMoveFactory() {
        return new NonImmediateMoveFactory();
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
    public List<Side> getPlayingSides() {
        List<Side> sides = new ArrayList<>();

        Side whiteSide = new Side(
                "White",
                new EnglishCheckersPieceSetter(getBoardSize() - 3, getBoardSize() - 1),
                new EnglishCheckersKingRow(0),
                new EnglishCheckersPieceFactory(EnglishCheckersPieceFactory.WHITE_PIECE_DIRECTIONS, EnglishCheckersPieceFactory.WHITE_KING_DIRECTIONS)
        );

        Side blackSide = new Side(
                "Black",
                new EnglishCheckersPieceSetter(0, 2),
                new EnglishCheckersKingRow(getBoardSize() - 1),
                new EnglishCheckersPieceFactory(EnglishCheckersPieceFactory.BLACK_PIECE_DIRECTIONS, EnglishCheckersPieceFactory.BLACK_KING_DIRECTIONS)
        );

        whiteSide.setNext(blackSide);
        blackSide.setNext(whiteSide);

        sides.add(whiteSide);
        sides.add(blackSide);

        return sides;
    }

    @Override
    public Side processWinningConditions(Board board, List<Move> availableMoves, List<Side> playingSides, Side current) {
        for(int i = 0; i < playingSides.size(); i++) {
            Side playingSide = playingSides.get(i % playingSides.size());
            if(playingSide.getPieces(board).isEmpty())
                return playingSides.get((i + 1) % playingSides.size());
        }

        if(availableMoves.isEmpty())
            return new Side("DRAW", null, null, null);

        return null;
    }

    @Override
    public CaptureConstraints getCaptureConstraints(Board board, MoveHistory history, Move move) {
        return new EnglishCheckersCaptureConstraints(board, history, move);
    }
}
