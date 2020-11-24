package backend.rulesets.english;

import backend.Board;
import backend.manager.MoveHistory;
import backend.moves.base.Move;
import backend.moves.factory.MoveFactory;
import backend.moves.factory.NonImmediateMoveFactory;
import backend.rulesets.CaptureConstraints;
import backend.rulesets.CheckersRuleset;
import backend.side.Side;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EnglishCheckers implements CheckersRuleset {

    public final static UUID OID = UUID.fromString("184f54a6-7495-4784-be5d-980e06cf1230");

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
