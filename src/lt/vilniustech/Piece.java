package lt.vilniustech;

import lt.vilniustech.side.Side;

import java.util.function.Supplier;

public class Piece {

    public Piece(Side side, Direction[] directions, int moveSize, Supplier<Piece> promoter){
        this.side = side;
        this.directions = directions;
        this.moveSize = moveSize;
        this.promotionLevel = 0;
        this.promoter = promoter;
    }

    public Piece(Side side, Direction[] directions, int moveSize){
        this(side, directions, moveSize, null);
    }

    public Direction[] getDirections() {
        return directions;
    }

    public Side getSide() {
        return side;
    }

    public boolean isKing() { return promotionLevel > 0; }

    public int getMoveSize() {
        return moveSize;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    private final Supplier<Piece> promoter;
    public Piece promote() {
        if(promoter == null)
            return this;

        Piece promoted = promoter.get();
        promoted.promotionLevel = promotionLevel + 1;
        return promoted;
    }
    private Coordinate coordinate;
    private final Side side;
    private final Direction[] directions;
    private final int moveSize;
    private int promotionLevel;
}
