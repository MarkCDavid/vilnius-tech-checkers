package lt.vilniustech.rulesets.international;

import lt.vilniustech.Coordinate;
import lt.vilniustech.side.PieceSetter;

public class InternationalCheckersCellFill extends PieceSetter {

    public InternationalCheckersCellFill(int rowFrom, int rowTo) {
        this.rowFrom = Math.min(rowFrom, rowTo);
        this.rowTo  = Math.max(rowFrom, rowTo);
    }

    @Override
    protected boolean setRow(Coordinate coordinate) {
        int row = coordinate.getRow();
        return row >= rowFrom && row <= rowTo;
    }

    @Override
    protected boolean setColumn(Coordinate coordinate) {
        return (coordinate.getRow() + coordinate.getColumn() + 1) % 2 == 0;
    }

    private final int rowFrom;
    private final int rowTo;
}