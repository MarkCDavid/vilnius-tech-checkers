package lt.vilniustech;

public enum Side {
    BLACK,
    WHITE,
    DRAW,
    NONE;

    public static Side opposite(Side side) {
        switch (side){
            case BLACK -> { return WHITE; }
            case WHITE -> { return BLACK; }
            default -> throw new IllegalStateException("Unexpected value: " + side);
        }
    }
}
