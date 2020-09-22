package lt.vilniustech;

public class Piece {

    public Direction[] getDirections() {
        return directions;
    }

    public Side getSide() {
        return side;
    }

    public boolean isKing() { return king; }

    public int getMoveSize() {
        return moveSize;
    }

    public Piece(Side side, Direction[] directions, int moveSize, boolean king){
        this.side = side;
        this.directions = directions;
        this.moveSize = moveSize;
        this.king = king;
    }

    public Piece(Side side, Direction[] directions, boolean king){
        this(side, directions, 1, king);
    }

    public Piece(Side side, Direction[] directions, int moveSize){
        this(side, directions, moveSize, false);
    }

    public Piece(Side side, Direction[] directions){
        this(side, directions, 1, false);
    }

    private final boolean king;
    private final Side side;
    private final Direction[] directions;
    private final int moveSize;
}
