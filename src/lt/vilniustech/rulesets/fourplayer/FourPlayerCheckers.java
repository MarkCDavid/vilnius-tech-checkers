package lt.vilniustech.rulesets.fourplayer;

import lt.vilniustech.Board;
import lt.vilniustech.manager.MoveHistory;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.moves.factory.MoveFactory;
import lt.vilniustech.moves.factory.NonImmediateMoveFactory;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.side.Side;

import java.util.ArrayList;
import java.util.List;

public class FourPlayerCheckers implements CheckersRuleset {

    @Override
    public String toString() {
        return "Four Player Checkers";
    }

    @Override
    public int getBoardSize() {
        return 12;
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

        Side p1 = new Side(
                "Player 1",
                new FourPlayerCheckersPieceSetter(getBoardSize() / 4, 3 * getBoardSize() / 4 - 1, 0, 1),
                new FourPlayerCheckersKingRow(3 * getBoardSize() / 8, 5 * getBoardSize() / 8, 3 * getBoardSize() / 8, 5 * getBoardSize() / 8 ),
                new FourPlayerCheckersPieceFactory(FourPlayerCheckersPieceFactory.DIRECTIONS, FourPlayerCheckersPieceFactory.DIRECTIONS)
        );


        Side p2 = new Side(
                "Player 2",
                new FourPlayerCheckersPieceSetter(getBoardSize() / 4, 3 * getBoardSize() / 4 - 1, getBoardSize() - 2, getBoardSize() - 1),
                new FourPlayerCheckersKingRow(3 * getBoardSize() / 8, 5 * getBoardSize() / 8, 3 * getBoardSize() / 8, 5 * getBoardSize() / 8 ),
                new FourPlayerCheckersPieceFactory(FourPlayerCheckersPieceFactory.DIRECTIONS, FourPlayerCheckersPieceFactory.DIRECTIONS)
        );


        Side p3 = new Side(
                "Player 3",
                new FourPlayerCheckersPieceSetter(0, 1, getBoardSize() / 4, 3 * getBoardSize() / 4 - 1),
                new FourPlayerCheckersKingRow(3 * getBoardSize() / 8, 5 * getBoardSize() / 8, 3 * getBoardSize() / 8, 5 * getBoardSize() / 8 ),
                new FourPlayerCheckersPieceFactory(FourPlayerCheckersPieceFactory.DIRECTIONS, FourPlayerCheckersPieceFactory.DIRECTIONS)
        );


        Side p4 = new Side(
                "Player 4",
                new FourPlayerCheckersPieceSetter(getBoardSize() - 2, getBoardSize() - 1, getBoardSize() / 4, 3 * getBoardSize() / 4 - 1),
                new FourPlayerCheckersKingRow(3 * getBoardSize() / 8, 5 * getBoardSize() / 8, 3 * getBoardSize() / 8, 5 * getBoardSize() / 8 ),
                new FourPlayerCheckersPieceFactory(FourPlayerCheckersPieceFactory.DIRECTIONS, FourPlayerCheckersPieceFactory.DIRECTIONS)
        );

        p1.setNext(p2);
        p2.setNext(p3);
        p3.setNext(p4);
        p4.setNext(p1);

        sides.add(p1);
        sides.add(p2);
        sides.add(p3);
        sides.add(p4);

        return sides;
    }

    @Override
    public String processWinningConditions(Board board, List<Move> availableMoves, List<Side> playingSides, Side current) {
        for(int i = 0; i < playingSides.size(); i++) {
            Side playingSide = playingSides.get(i % playingSides.size());
            if(playingSide.getPieces(board).isEmpty())
                return playingSides.get((i + 1) % playingSides.size()).toString();
        }

        if(availableMoves.isEmpty())
            return "Draw";

        return null;
    }

    @Override
    public CaptureConstraints getCaptureConstraints(Board board, MoveHistory history, Move move) {
        return new FourPlayerCheckersCaptureConstraints(board, history, move);
    }
}
