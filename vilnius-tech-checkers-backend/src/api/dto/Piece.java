package api.dto;

public class Piece {

    public Piece(Side side, Coordinate coordinate, boolean isKing) {
        this.side = side;
        this.coordinate = coordinate;
        this.isKing = isKing;
    }

    public Side getSide() {
        return side;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public boolean isKing() {
        return isKing;
    }

    private final Side side;
    private final Coordinate coordinate;
    private final boolean isKing;
}
