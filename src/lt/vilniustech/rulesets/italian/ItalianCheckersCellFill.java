package lt.vilniustech.rulesets.italian;

import lt.vilniustech.Coordinate;
import lt.vilniustech.side.PieceSetter;

public class ItalianCheckersCellFill extends PieceSetter {

    public ItalianCheckersCellFill(int rowFrom, int rowTo) {
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
        int offset = (coordinate.getRow() + 1) % 2;
        int column = coordinate.getColumn();
        return (column + offset) % 2 == 0;
    }

    private final int rowFrom;
    private final int rowTo;
}