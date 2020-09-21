package lt.vilniustech;

public class Direction {

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction(int x, int y) {
        this.x = bound(x);
        this.y = bound(y);
    }

    @SuppressWarnings("ManualMinMaxCalculation")
    private int bound(int value) {
        if(value < -1) return -1;
        if(value > 1) return 1;
        return value;
    }

    private final int x;
    private final int y;
}
