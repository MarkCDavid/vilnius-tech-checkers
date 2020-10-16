package lt.vilniustech.rulesets.fourplayer;

import lt.vilniustech.Direction;
import lt.vilniustech.Piece;
import lt.vilniustech.side.PieceFactory;

import java.util.function.Supplier;

public class FourPlayerCheckersPieceFactory extends PieceFactory {

    public FourPlayerCheckersPieceFactory(Direction[] pieceDirections, Direction[] kingDirections) {
        this.pieceDirections = pieceDirections;
        this.kingDirections = kingDirections;
    }

    @Override
    public Piece producePiece() {
        return new Piece(side, pieceDirections, 2, 2, kingProducer());
    }

    private Supplier<Piece> kingProducer() {
        return () -> new Piece(side, kingDirections, 6, 6);
    }

    private final Direction[] pieceDirections;
    private final Direction[] kingDirections;


    protected static final Direction[] DIRECTIONS = new Direction[]{
            new Direction(1, 0),
            new Direction(-1, 0),
            new Direction(0, 1),
            new Direction(0, -1),
    };
}
