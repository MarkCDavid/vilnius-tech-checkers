package backend.rulesets.english;

import backend.Coordinate;
import backend.side.CoordinateValidator;

public class EnglishCheckersKingRow extends CoordinateValidator {

    public EnglishCheckersKingRow(int row) {
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
