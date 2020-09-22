package lt.vilniustech.consolerenderer;

import lt.vilniustech.Coordinate;

import java.util.Arrays;

public class Buffer {

    public Buffer(int boardSize) {
        this.buffer = constructBuffer(boardSize);
        this.boardOriginX = getIndexBufferOffset(boardSize);
        this.boardOriginY = getIndexBufferOffset(boardSize);
        fillVerticalIndexes(boardSize);
        fillHorizontalIndexes(boardSize);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder((buffer.length + 1) * buffer.length);
        for(char[] row: buffer) {
            stringBuilder.append(row);
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    public void fillShape(char[][] shape, int row, int column, char symbol) {
        int x = boardOriginX + row * Shapes.VERTICAL_OFFSET;
        int y = boardOriginY + column * Shapes.HORIZONTAL_OFFSET;

        for (int shapeRow = 0; shapeRow < shape.length; shapeRow++) {
            for (int shapeColumn = 0; shapeColumn < shape[shapeRow].length; shapeColumn++) {
                if(Shapes.inCenter(shapeRow, shapeColumn)) {
                    buffer[x + shapeRow][y + shapeColumn] = symbol;
                } else {
                    buffer[x + shapeRow][y + shapeColumn] = shape[shapeRow][shapeColumn];
                }
            }
        }
    }

    private void fillVerticalIndexes(int boardSize) {
        int x = boardOriginX - 1;
        int y = boardOriginY + Shapes.HORIZONTAL_OFFSET / 2;

        for (int i = 0; i < boardSize; i++) {
            String index = Coordinate.toStringIndex(i);

            for (int xOffset = 0; xOffset < index.length(); xOffset++)
                buffer[x + xOffset][y] = index.charAt(xOffset);

            y += Shapes.HORIZONTAL_OFFSET;
        }
    }

    private void fillHorizontalIndexes(int boardSize) {
        int x = boardOriginX + Shapes.VERTICAL_OFFSET / 2;
        int y = boardOriginY - 1;

        for (int i = 0; i < boardSize; i++) {
            String index = String.valueOf(i);

            for (int yOffset = 0; yOffset < index.length(); yOffset++)
                buffer[x][y - yOffset] = index.charAt(yOffset);

            x += Shapes.VERTICAL_OFFSET;
        }
    }

    private static int getIndexBufferOffset(int boardSize) {
        return String.valueOf(boardSize).length();
    }

    private static int getBoardBufferWidth(int boardSize) {
        return Shapes.HORIZONTAL_OFFSET * boardSize + 1;
    }

    private static int getBoardBufferHeight(int boardSize) {
        return Shapes.VERTICAL_OFFSET * boardSize + 1;
    }

    private static int getFullBufferWidth(int boardSize) {
        return getBoardBufferWidth(boardSize) + getIndexBufferOffset(boardSize);
    }

    private static int getFullBufferHeight(int boardSize) {
        return getBoardBufferHeight(boardSize) + getIndexBufferOffset(boardSize);
    }

    private static char[][] constructBuffer(int boardSize) {
        char[][] buffer = new char[getFullBufferHeight(boardSize)][getFullBufferWidth(boardSize)];
        for (char[] chars : buffer)
            Arrays.fill(chars, ' ');
        return buffer;
    }

    private final int boardOriginX;
    private final int boardOriginY;
    private final char[][] buffer;
}
