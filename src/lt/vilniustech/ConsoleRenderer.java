package lt.vilniustech;

import lt.vilniustech.rulesets.CheckersRuleset;

import java.util.Arrays;

public class ConsoleRenderer implements Renderer {

    private static final char EMPTY_CELL = ' ';
    private static final char WHITE_PIECE = '⛀';
    private static final char WHITE_KING = '⛁';
    private static final char BLACK_PIECE = '⛂';
    private static final char BLACK_KING = '⛃';

    private static final int HORIZONTAL_OFFSET = 4;
    private static final int VERTICAL_OFFSET = 2;


    @Override
    public void render(Board board) {

        CheckersRuleset ruleset = board.getRuleset();
        char[][] buffer = constructBuffer(ruleset.getBoardSize());

        int x = getIndexBufferSize(ruleset.getBoardSize());
        int y = getIndexBufferSize(ruleset.getBoardSize());
        fillVerticalIndexes(buffer, x, y, ruleset.getBoardSize());
        fillHorizontalIndexes(buffer, x, y, ruleset.getBoardSize());

        for(int row = 0; row < ruleset.getBoardSize(); row++) {
            for(int column = 0; column < ruleset.getBoardSize(); column++) {
                Coordinate coordinate = new Coordinate(column, row);
                char symbol = getRepresentation(board.getCell(coordinate).getPiece());
                fillShape(
                        buffer,
                        getShape(row, column, ruleset.getBoardSize()),
                        x + row * VERTICAL_OFFSET,
                        y + column * HORIZONTAL_OFFSET,
                        symbol
                );
            }
        }

        for (int i = 0; i < buffer.length; i++) {
            System.out.println(buffer[i]);
        }
    }

    private void fillVerticalIndexes(char[][] buffer, int originX, int originY, int boardSize) {
        originX -= 1;
        originY += HORIZONTAL_OFFSET / 2;

        for (int i = 0; i < boardSize; i++) {
            String index = String.valueOf(i);
            for (int xe = 0; xe < index.length(); xe++) {
                buffer[originX + xe][originY] = index.charAt(xe);
            }
            originY += HORIZONTAL_OFFSET;
        }
    }

    private void fillHorizontalIndexes(char[][] buffer, int originX, int originY, int boardSize) {
        originX += VERTICAL_OFFSET / 2;
        originY -= 1;

        for (int i = 0; i < boardSize; i++) {
            String index = String.valueOf(i);
            for (int ye = 0; ye < index.length(); ye++) {
                buffer[originX][originY - ye] = index.charAt(ye);
            }
            originX += VERTICAL_OFFSET;
        }
    }

    private void fillShape(char[][] buffer, char[][] shape, int atX, int atY, char symbol) {
        for (int row = 0; row < shape.length; row++) {
            for (int column = 0; column < shape[row].length; column++) {
                buffer[atX + row][atY + column] = shape[row][column];
                if (row == 1 && column == 2) buffer[atX + row][atY + column] = symbol;
            }
        }
    }


    private char getRepresentation(Piece piece) {
        if (piece == null) {
            return EMPTY_CELL;
        } else if (piece.getSide() == Side.BLACK) {
            return piece.isKing() ? BLACK_KING : BLACK_PIECE;
        } else if (piece.getSide() == Side.WHITE) {
            return piece.isKing() ? WHITE_KING : WHITE_PIECE;
        } else {
            throw new IllegalStateException();
        }
    }

    // 5 * n - (n - 1) : width of all board given size of board n
    // 5 * n - n + 1
    // 4 * n + 1
    private static int getBoardBufferWidth(int boardSize) {
        return HORIZONTAL_OFFSET * boardSize + 1;
    }

    private static int getBoardBufferHeight(int boardSize) {
        return VERTICAL_OFFSET * boardSize + 1;
    }

    private static int getIndexBufferSize(int boardSize) {
        return String.valueOf(boardSize).length();
    }

    private static int getFullBufferWidth(int boardSize) {
        return getBoardBufferWidth(boardSize) + getIndexBufferSize(boardSize);
    }

    private static int getFullBufferHeight(int boardSize) {
        return getBoardBufferHeight(boardSize) + getIndexBufferSize(boardSize);
    }

    private char[][] constructBuffer(int boardSize) {
        char[][] buffer = new char[getFullBufferHeight(boardSize)][getFullBufferWidth(boardSize)];
        for (char[] chars : buffer)
            Arrays.fill(chars, ' ');
        return buffer;
    }

    // @formatter:off
    private char[][] getShape(int row, int column, int boardSize) {
        if(row == 0             && column == 0            ) return getTopLeftShape();
        if(row == 0             && column == boardSize - 1) return getTopRightShape();
        if(row == boardSize - 1 && column == 0            ) return getBottomLeftShape();
        if(row == boardSize - 1 && column == boardSize - 1) return getBottomRightShape();
        if(row    == 0            ) return getTopShape();
        if(row    == boardSize - 1) return getBottomShape();
        if(column == 0            ) return getLeftShape();
        if(column == boardSize - 1) return getRightShape();
        return getMiddleShape();
    }


    private char[][] getMiddleShape() {
        String shape =
        "╬═══╬\n" +
        "║   ║\n" +
        "╬═══╬";
        return stringToShape(shape);
    }

    private char[][] getTopLeftShape() {
        String shape =
        "╔═══╦\n" +
        "║   ║\n" +
        "╠═══╬";
        return stringToShape(shape);
    }

    private char[][] getTopRightShape() {
        String shape =
        "╦═══╗\n" +
        "║   ║\n" +
        "╬═══╣";
        return stringToShape(shape);
    }

    private char[][] getBottomLeftShape() {
        String shape =
        "╠═══╬\n" +
        "║   ║\n" +
        "╚═══╩";
        return stringToShape(shape);
    }

    private char[][] getBottomRightShape() {
        String shape =
        "╬═══╣\n" +
        "║   ║\n" +
        "╩═══╝";
        return stringToShape(shape);
    }

    private char[][] getLeftShape() {
        String shape =
        "╠═══╬\n" +
        "║   ║\n" +
        "╠═══╬";
        return stringToShape(shape);
    }

    private char[][] getTopShape() {
        String shape =
        "╦═══╦\n" +
        "║   ║\n" +
        "╬═══╬";
        return stringToShape(shape);
    }

    private char[][] getRightShape() {
        String shape =
        "╬═══╣\n" +
        "║   ║\n" +
        "╬═══╣";
        return stringToShape(shape);
    }

    private char[][] getBottomShape() {
        String shape =
        "╬═══╬\n" +
        "║   ║\n" +
        "╩═══╩";
        return stringToShape(shape);
    }
    // @formatter:on

    private char[][] stringToShape(String shapeString) {
        String[] rows = shapeString.split("\n");
        char[][] shape = new char[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            shape[i] = rows[i].toCharArray();
        }
        return shape;
    }
}