package api.dto;

public class Cell {

    public Cell(Coordinate coordinate, Piece piece) {
        this.coordinate = coordinate;
        this.piece = piece;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Piece getPiece() {
        return piece;
    }

    private final Coordinate coordinate;
    private final Piece piece;
}
