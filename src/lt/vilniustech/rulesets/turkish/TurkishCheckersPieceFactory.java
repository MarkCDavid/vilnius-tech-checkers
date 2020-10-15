package lt.vilniustech.rulesets.turkish;

import lt.vilniustech.Direction;
import lt.vilniustech.Piece;
import lt.vilniustech.side.PieceFactory;

import java.util.function.Supplier;

public class TurkishCheckersPieceFactory extends PieceFactory {

    public TurkishCheckersPieceFactory(Direction[] pieceDirections, Direction[] kingDirections, int boardSize) {
        this.pieceDirections = pieceDirections;
        this.kingDirections = kingDirections;
        this.boardSize = boardSize;
    }

    @Override
    public Piece producePiece() {
        return new Piece(side, pieceDirections, 1, 1, kingProducer());
    }

    private Supplier<Piece> kingProducer() {
        return () -> new Piece(side, kingDirections, boardSize, boardSize);
    }

    private final Direction[] pieceDirections;
    private final Direction[] kingDirections;
    private final int boardSize;


    protected static final Direction[] BLACK_PIECE_DIRECTIONS = new Direction[]{
            new Direction(1, 0),
            new Direction(-1, 0),
            new Direction(0, 1),
    };

    protected static final Direction[] BLACK_KING_DIRECTIONS = new Direction[]{
            new Direction(1, 0),
            new Direction(-1, 0),
            new Direction(0, 1),
            new Direction(0, -1),
    };

    protected static final Direction[] WHITE_PIECE_DIRECTIONS = new Direction[]{
            new Direction(1, 0),
            new Direction(-1, 0),
            new Direction(0, -1),
    };

    protected static final Direction[] WHITE_KING_DIRECTIONS = new Direction[]{
            new Direction(1, 0),
            new Direction(-1, 0),
            new Direction(0, 1),
            new Direction(0, -1),
    };
}
