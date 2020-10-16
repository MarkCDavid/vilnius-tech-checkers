package backend.rulesets.turkish;

import backend.Coordinate;
import backend.side.CoordinateValidator;

public class TurkishCheckersKingRow extends CoordinateValidator {

    public TurkishCheckersKingRow(int row) {
        this.row = row;
    }

    @Override
    protected boolean rowValid(Coordinate coordinate) {
        return coordinate.getRow() == row;
    }

    @Override
    protected boolean columnValid(Coordinate coordinate) {
        return true;
    }

    private final int row;
}
