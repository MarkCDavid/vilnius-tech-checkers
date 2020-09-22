package lt.vilniustech.consolecheckers;

public class Shapes {

    public static final int SHAPE_WIDTH = 5;
    public static final int SHAPE_HEIGHT = 3;

    public static final int HORIZONTAL_OFFSET = SHAPE_WIDTH - 1;
    public static final int VERTICAL_OFFSET = SHAPE_HEIGHT - 1;

    public static final int HORIZONTAL_CENTER = HORIZONTAL_OFFSET / 2;
    public static final int VERTICAL_CENTER = VERTICAL_OFFSET / 2;

    public static boolean inCenter(int x, int y) {
        return x == VERTICAL_CENTER && y == HORIZONTAL_CENTER;
    }

    // @formatter:off
    public static char[][] getShape(int row, int column, int boardSize) {
        if(row == 0             && column == 0            ) return topLeft;
        if(row == 0             && column == boardSize - 1) return topRight;
        if(row == boardSize - 1 && column == 0            ) return bottomLeft;
        if(row == boardSize - 1 && column == boardSize - 1) return bottomRight;
        if(row    == 0            ) return top;
        if(row    == boardSize - 1) return bottom;
        if(column == 0            ) return left;
        if(column == boardSize - 1) return right;
        return middle;
    }

    private static final char[][] middle = stringToShape(
        "╬═══╬\n" +
        "║   ║\n" +
        "╬═══╬"
    );

    private static final char[][] topLeft = stringToShape(
        "╔═══╦\n" +
        "║   ║\n" +
        "╠═══╬"
    );

    private static final char[][] topRight = stringToShape(
        "╦═══╗\n" +
        "║   ║\n" +
        "╬═══╣"
    );

    private static final char[][] bottomLeft = stringToShape(
        "╠═══╬\n" +
        "║   ║\n" +
        "╚═══╩"
    );

    private static final char[][] bottomRight = stringToShape(
        "╬═══╣\n" +
        "║   ║\n" +
        "╩═══╝"
    );

    private static final char[][] left = stringToShape(
        "╠═══╬\n" +
        "║   ║\n" +
        "╠═══╬"
    );

    private static final char[][] top = stringToShape(
        "╦═══╦\n" +
        "║   ║\n" +
        "╬═══╬"
    );

    private static final char[][] right = stringToShape(
        "╬═══╣\n" +
        "║   ║\n" +
        "╬═══╣"
    );

    private static final char[][] bottom = stringToShape(
        "╬═══╬\n" +
        "║   ║\n" +
        "╩═══╩"
    );
    // @formatter:on


    private static char[][] stringToShape(String shapeString) {
        String[] rows = shapeString.split("\n");
        char[][] shape = new char[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            shape[i] = rows[i].toCharArray();
        }
        return shape;
    }
}
