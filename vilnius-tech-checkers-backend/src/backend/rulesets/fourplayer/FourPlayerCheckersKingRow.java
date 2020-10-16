package backend.rulesets.fourplayer;

import backend.Coordinate;
import backend.side.CoordinateValidator;

public class FourPlayerCheckersKingRow extends CoordinateValidator {

    public FourPlayerCheckersKingRow(int rowFrom, int rowTo, int columnFrom, int columnTo) {
        this.rowFrom = Math.min(rowFrom, rowTo);
        this.rowTo  = Math.max(rowFrom, rowTo);
        this.columnFrom = Math.min(columnFrom, columnTo);
        this.columnTo  = Math.max(columnFrom, columnTo);
    }

    @Override
    protected boolean rowValid(Coordinate coordinate) {
        int row = coordinate.getRow();
        return row >= rowFrom && row <= rowTo;
    }

    @Override
    protected boolean columnValid(Coordinate coordinate) {
        int column = coordinate.getColumn();
        return column >= columnFrom && column <= columnTo;
    }

    private final int rowFrom;
    private final int rowTo;
    private final int columnFrom;
    private final int columnTo;
}
