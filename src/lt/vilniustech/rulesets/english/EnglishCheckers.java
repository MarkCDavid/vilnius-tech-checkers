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
        if(availableMoves.isEmpty()) {
            playingSides.remove(current);
        }

        List<Side> toRemove = new ArrayList<>();
        for(Side side: playingSides) {
            if(side.getPieces(board).isEmpty())
                toRemove.add(side);
        }

        playingSides.removeAll(toRemove);
        return current;
    }

    @Override
    public CaptureConstraints getCaptureConstraints(Board board, MoveHistory history, Move move) {
        return new EnglishCheckersCaptureConstraints(board, history, move);
    }
}
