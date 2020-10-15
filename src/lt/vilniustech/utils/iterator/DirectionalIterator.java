package lt.vilniustech.utils.iterator;

import lt.vilniustech.Coordinate;
import lt.vilniustech.Direction;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DirectionalIterator implements Iterator<Coordinate> {

    public DirectionalIterator(Coordinate from, Coordinate to, boolean includeLast) {
        this.direction = new Direction(from, to);

        this.to = includeLast ? to : to.move(direction.inverse());
        this.from = from;

        this.current = this.from;
    }

    @Override
    public boolean hasNext() {
        return !current.equals(to);
    }

    @Override
    public Coordinate next() {
        if(!hasNext()) {
            throw new NoSuchElementException();
        }
        current = current.move(direction);
        return current;
    }

    private Coordinate current;

    private final Coordinate from;
    private final Coordinate to;
    private final Direction direction;
}