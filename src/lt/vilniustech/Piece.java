package lt.vilniustech;

public class Piece {

    public Direction[] getAvailableDirections() {
        return availableDirections;
    }

    public char getSide() {
        return side;
    }

    public int getMoveSize() {
        return moveSize;
    }

    public Piece(char side, int moveSize){
        this.side = side;
        this.availableDirections = new Direction[] { new Direction(1, -1), new Direction(-1, -1),};
        this.moveSize = moveSize;
    }

    private final Direction[] availableDirections;
    private final int moveSize;
    private final char side;
}
