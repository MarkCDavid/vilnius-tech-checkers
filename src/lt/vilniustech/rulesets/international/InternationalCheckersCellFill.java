package lt.vilniustech.rulesets.international;

import lt.vilniustech.Coordinate;
import lt.vilniustech.rulesets.CellFill;

public class InternationalCheckersCellFill extends CellFill {

    public InternationalCheckersCellFill(int rowFrom, int rowTo) {
        this.rowFrom = Math.min(rowFrom, rowTo);
        this.rowTo  = Math.max(rowFrom, rowTo);
    }

    @Override
    protected boolean fillRow(Coordinate coordinate) {
        int row = coordinate.getRow();
        return row >= rowFrom && row <= rowTo;
    }

    @Override
    protected boolean fillColumn(Coordinate coordinate) {
        return (coordinate.getRow() + coordinate.getColumn() + 1) % 2 == 0;
    }

    private final int rowFrom;
    private final int rowTo;
}