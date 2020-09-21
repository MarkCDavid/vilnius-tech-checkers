package lt.vilniustech;

public class Coordinate {

    public int getIndex(int size) {
        return y * size + x;
    }

    public Coordinate move(Direction direction){
        return move(direction, 1);
    }

    public Coordinate move(Direction direction, int size){
        return new Coordinate(this.x + direction.getX() * size, this.y + direction.getY() * size);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private final int x;
    private final int y;
}
