package backend.rulesets.english;

import backend.Direction;
import backend.Piece;
import backend.side.PieceFactory;

import java.util.function.Supplier;

public class EnglishCheckersPieceFactory extends PieceFactory {

    public EnglishCheckersPieceFactory(Direction[] pieceDirections, Direction[] kingDirections) {
        this.pieceDirections = pieceDirections;
        this.kingDirections = kingDirections;
    }

    @Override
    public Piece producePiece() {
        return new Piece(side, pieceDirections, 1, 1, kingProducer());
    }

    private Supplier<Piece> kingProducer() {
        return () -> new Piece(side, kingDirections, 1, 1);
    }

    private final Direction[] pieceDirections;
    private final Direction[] kingDirections;


    public static final Direction[] BLACK_PIECE_DIRECTIONS = new Direction[]{
            new Direction(1, 1),
            new Direction(-1, 1),
    };

    public static final Direction[] BLACK_KING_DIRECTIONS = new Direction[]{
            new Direction(1, -1),
            new Direction(-1, -1),
            new Direction(1, 1),
            new Direction(-1, 1),
    };

    public static final Direction[] WHITE_PIECE_DIRECTIONS = new Direction[]{
            new Direction(1, -1),
            new Direction(-1, -1),
    };

    public static final Direction[] WHITE_KING_DIRECTIONS = new Direction[]{
            new Direction(1, -1),
            new Direction(-1, -1),
            new Direction(1, 1),
            new Direction(-1, 1),
    };
}
