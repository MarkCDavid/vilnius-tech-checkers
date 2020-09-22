package lt.vilniustech;

public class Piece {

    public Direction[] getDirections() {
        return directions;
    }

    public Side getSide() {
        return side;
    }

    public int getMoveSize() {
        return moveSize;
    }

    public Piece(Side side, Direction[] directions, int moveSize){
        this.side = side;
        this.directions = directions;
        this.moveSize = moveSize;
    }

    public Piece(Side side, Direction[] directions){
        this.side = side;
        this.directions = directions;
        this.moveSize = 1;
    }

    private final Side side;
    private final Direction[] directions;
    private final int moveSize;
}
