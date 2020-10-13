package lt.vilniustech.side;
import lt.vilniustech.Coordinate;

public abstract class PieceSetter {

    public boolean setPiece(Coordinate coordinate) {
        return setRow(coordinate) && setColumn(coordinate);
    }
    protected abstract boolean setRow(Coordinate coordinate);
    protected abstract boolean setColumn(Coordinate coordinate);
}
