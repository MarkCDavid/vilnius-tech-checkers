package lt.vilniustech.rulesets.english;

import lt.vilniustech.Direction;
import lt.vilniustech.Piece;
import lt.vilniustech.side.PieceFactory;

import java.util.function.Supplier;

public class EnglishCheckersBlackPieceFactory extends PieceFactory {

    @Override
    public Piece producePiece() {
        return new Piece(side, pieceDirections, 1, kingProducer());
    }

    private Supplier<Piece> kingProducer() {
        return () -> new Piece(side, kingDirections, 1);
    }

    private static final Direction[] pieceDirections = new Direction[]{
            new Direction(1, 1),
            new Direction(-1, 1),
    };

    private static final Direction[] kingDirections = new Direction[]{
            new Direction(1, -1),
            new Direction(-1, -1),
            new Direction(1, 1),
            new Direction(-1, 1),
    };

}