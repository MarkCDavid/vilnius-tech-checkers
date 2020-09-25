package lt.vilniustech.rulesets.turkish;

import lt.vilniustech.Coordinate;
import lt.vilniustech.rulesets.CellFill;

public class TurkishCheckersCellFill extends CellFill {

    public TurkishCheckersCellFill(int rowFrom, int rowTo) {
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
        return true;
    }

    private final int rowFrom;
    private final int rowTo;
}