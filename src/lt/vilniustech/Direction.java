package lt.vilniustech;

public class Direction {

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isCaptureOnly() {
        return isCaptureOnly;
    }

    public Direction(int x, int y) {
        this(x, y, false);
    }

    public Direction(int x, int y, boolean isCaptureOnly) {
        this.x = bound(x);
        this.y = bound(y);
        this.isCaptureOnly = isCaptureOnly;
    }

    @SuppressWarnings("ManualMinMaxCalculation")
    private int bound(int value) {
        if(value < -1) return -1;
        if(value > 1) return 1;
        return value;
    }

    private final int x;
    private final int y;
    private final boolean isCaptureOnly;
}
