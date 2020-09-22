package lt.vilniustech;

import java.util.Iterator;

public class BoardIterator implements Iterator<Cell> {

    public BoardIterator(Board board) {
        this.board = board;
        this.index = 0;
        this.size = board.getRuleset().getBoardSize();
        this.upperBound = size * size;
    }

    @Override
    public boolean hasNext() {
        return index >= 0 && index < upperBound;
    }

    @Override
    public Cell next() {
        return board.getCell(Coordinate.ofIndex(index++, size));
    }

    private int index;
    private final int size;
    private final int upperBound;
    private final Board board;
}
