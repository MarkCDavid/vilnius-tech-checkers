package lt.vilniustech.utils;

import lt.vilniustech.Coordinate;

import java.util.Iterator;

public class CoordinateIterator implements Iterable<Coordinate>, Iterator<Coordinate> {

    public CoordinateIterator(int size) {
        this.size = size;
        this.row = 0;
        this.column = 0;
    }

    @Override
    public boolean hasNext() {
        return row >= 0 && row < size;
    }

    @Override
    public Coordinate next() {
        Coordinate coordinate = new Coordinate(row, column);
        nextState();
        return coordinate;
    }

    private void nextState() {
        column++;
        if(column >= size) {
            column = 0;
            row++;
        }
    }

    private int row;
    private int column;
    private final int size;

    @Override
    public Iterator<Coordinate> iterator() {
        return this;
    }
}
