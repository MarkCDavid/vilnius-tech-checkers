package lt.vilniustech;

public class EnglishCheckersCellFill extends CellFill {

    public EnglishCheckersCellFill(int rowFrom, int rowTo) {
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
        int offset = (coordinate.getRow() + 1) % 2;
        int column = coordinate.getColumn();
        return (column + offset) % 2 == 0;
    }

    private final int rowFrom;
    private final int rowTo;
}
