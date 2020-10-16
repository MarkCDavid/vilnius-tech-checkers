package backend.side;
import backend.Coordinate;

public abstract class CoordinateValidator {

    public boolean isValid(Coordinate coordinate) {
        return rowValid(coordinate) && columnValid(coordinate);
    }
    protected abstract boolean rowValid(Coordinate coordinate);
    protected abstract boolean columnValid(Coordinate coordinate);
}
