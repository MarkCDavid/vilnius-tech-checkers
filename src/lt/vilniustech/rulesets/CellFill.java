package lt.vilniustech.rulesets;
import lt.vilniustech.Coordinate;

public abstract class CellFill {

    public boolean fillCell(Coordinate coordinate) {
        return fillRow(coordinate) && fillColumn(coordinate);
    }
    protected abstract boolean  fillRow(Coordinate coordinate);
    protected abstract boolean  fillColumn(Coordinate coordinate);
}
