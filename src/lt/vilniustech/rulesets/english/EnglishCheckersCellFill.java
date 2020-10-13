package lt.vilniustech.rulesets.english;

import lt.vilniustech.side.PieceSetter;
import lt.vilniustech.Coordinate;

public class EnglishCheckersCellFill extends PieceSetter {

    public EnglishCheckersCellFill(int rowFrom, int rowTo) {
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
