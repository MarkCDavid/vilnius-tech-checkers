package backend.rulesets.fourplayer;

import backend.Board;
import backend.manager.MoveHistory;
import backend.moves.base.Move;
import backend.moves.factory.MoveFactory;
import backend.moves.factory.NonImmediateMoveFactory;
import backend.rulesets.CaptureConstraints;
import backend.rulesets.CheckersRuleset;
import backend.side.CoordinateValidator;
import backend.side.Side;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FourPlayerCheckers implements CheckersRuleset {

    public final static UUID OID = UUID.fromString("2d3126b5-45eb-4c93-8704-ec3a291de472");

    @Override
    public String toString() {
        return "Four Player Checkers";
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
        return false;
    }

    @Override
    public List<Side> getPlayingSides() {
        List<Side> sides = new ArrayList<>();

        int middleStart = 3 * getBoardSize() / 8;
        int middleEnd = 4 * getBoardSize() / 8;


        CoordinateValidator middleKingRow = new FourPlayerCheckersKingRow(middleStart, middleEnd, middleStart, middleEnd);

        Side p1 = new Side(
                "Player 1",
                new FourPlayerCheckersPieceSetter(middleStart, middleEnd, 0, 1),
                middleKingRow,
                new FourPlayerCheckersPieceFactory(FourPlayerCheckersPieceFactory.DIRECTIONS, FourPlayerCheckersPieceFactory.DIRECTIONS)
        );


        Side p2 = new Side(
                "Player 2",
                new FourPlayerCheckersPieceSetter(middleStart, middleEnd, getBoardSize() - 2, getBoardSize() - 1),
                middleKingRow,
                new FourPlayerCheckersPieceFactory(FourPlayerCheckersPieceFactory.DIRECTIONS, FourPlayerCheckersPieceFactory.DIRECTIONS)
        );


        Side p3 = new Side(
                "Player 3",
                new FourPlayerCheckersPieceSetter(0, 1, middleStart, middleEnd),
                middleKingRow,
                new FourPlayerCheckersPieceFactory(FourPlayerCheckersPieceFactory.DIRECTIONS, FourPlayerCheckersPieceFactory.DIRECTIONS)
        );


        Side p4 = new Side(
                "Player 4",
                new FourPlayerCheckersPieceSetter(getBoardSize() - 2, getBoardSize() - 1, middleStart, middleEnd),
                middleKingRow,
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
    public Side processWinningConditions(Board board, List<Move> availableMoves, List<Side> playingSides, Side current) {

        List<Side> toRemove = new ArrayList<>();
        for(Side side: playingSides) {
            if(side.getPieces(board).isEmpty()) {
                toRemove.add(side);
            }
        }
        playingSides.removeAll(toRemove);

        if(availableMoves.isEmpty()) {
            playingSides.remove(current);
        }

        if(playingSides.isEmpty())
            return current;

        while(!playingSides.contains(current))
            current = current.getNext();

        return current;
    }

    @Override
    public CaptureConstraints getCaptureConstraints(Board board, MoveHistory history, Move move) {
        return new FourPlayerCheckersCaptureConstraints(board, history, move);
    }
}
