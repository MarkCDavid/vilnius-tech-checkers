package lt.vilniustech;

public class Coordinate {

    public int getIndex(int size) {
         return row * size + column;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public Coordinate move(Direction direction){
        return move(direction, 1);
    }

    public Coordinate move(Direction direction, int size){
        return new Coordinate(this.column + direction.getX() * size, this.row + direction.getY() * size);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", column, row);
    }

    public Coordinate(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public static Coordinate ofIndex(int index, int size) {
        return new Coordinate(index / size, index % size);
    }

    private final int column;
    private final int row;
}
