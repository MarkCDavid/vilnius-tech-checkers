package lt.vilniustech.rulesets.turkish;

import lt.vilniustech.Direction;
import lt.vilniustech.Piece;
import lt.vilniustech.side.PieceFactory;

import java.util.function.Supplier;

public class TurkishCheckersBlackPieceFactory extends PieceFactory {

    public TurkishCheckersBlackPieceFactory(int boardSize) {
        this.boardSize = boardSize;
    }

    @Override
    public Piece producePiece() {
        return new Piece(side, pieceDirections, 1, kingProducer());
    }

    private Supplier<Piece> kingProducer() {
        return () -> new Piece(side, kingDirections, boardSize);
    }

    private static final Direction[] pieceDirections = new Direction[]{
            new Direction(1, 0),
            new Direction(-1, 0),
            new Direction(0, 1),
    };

    private static final Direction[] kingDirections = new Direction[]{
            new Direction(1, 0),
            new Direction(-1, 0),
            new Direction(0, 1),
            new Direction(0, -1),
    };

    private final int boardSize;
}