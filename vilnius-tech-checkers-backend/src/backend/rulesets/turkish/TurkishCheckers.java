package backend.rulesets.turkish;

import backend.Board;
import backend.Piece;
import backend.manager.MoveHistory;
import backend.moves.base.Move;
import backend.moves.factory.ImmediateMoveFactory;
import backend.moves.factory.MoveFactory;
import backend.rulesets.CaptureConstraints;
import backend.rulesets.CheckersRuleset;
import backend.side.Side;

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
    public Side processWinningConditions(Board board, List<Move> availableMoves, List<Side> playingSides, Side current) {

        if (availableMoves.isEmpty()) {
            playingSides.clear();
            return current;
        }

        Side white = playingSides.get(0);
        Side black = playingSides.get(1);

        List<Piece> whitePieces = white.getPieces(board);
        List<Piece> blackPieces = black.getPieces(board);

        if (whitePieces.isEmpty()) {
            playingSides.remove(white);
        }

        if(blackPieces.isEmpty()) {
            playingSides.remove(black);
        }

        if(whitePieces.size() == 1 && blackPieces.size() == 1) {
            if (whitePieces.get(0).isKing() && !blackPieces.get(0).isKing()) {
                playingSides.remove(black);
            }

            if(blackPieces.get(0).isKing() && !whitePieces.get(0).isKing())
                playingSides.remove(white);
        }

        return current;
    }

    @Override
    public List<Side> getPlayingSides() {
        List<Side> sides = new ArrayList<>();

        Side whiteSide = new Side(
                "White",
                new TurkishCheckersPieceSetter(getBoardSize() - 3, getBoardSize() - 2),
                new TurkishCheckersKingRow(0),
                new TurkishCheckersPieceFactory(TurkishCheckersPieceFactory.WHITE_PIECE_DIRECTIONS, TurkishCheckersPieceFactory.WHITE_KING_DIRECTIONS, getBoardSize())
        );

        Side blackSide = new Side(
                "Black",
                new TurkishCheckersPieceSetter(1, 2),
                new TurkishCheckersKingRow(getBoardSize() - 1),
                new TurkishCheckersPieceFactory(TurkishCheckersPieceFactory.BLACK_PIECE_DIRECTIONS, TurkishCheckersPieceFactory.BLACK_KING_DIRECTIONS, getBoardSize())
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
