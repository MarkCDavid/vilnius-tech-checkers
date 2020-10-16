package backend.utils.iterator;

import backend.Coordinate;

import java.util.Iterator;

public class DirectionalIterable implements Iterable<Coordinate>  {

    public DirectionalIterable(Coordinate from, Coordinate to, boolean includeLast) {
        this.from = from;
        this.to = to;
        this.includeLast = includeLast;
    }

    @Override
    public Iterator<Coordinate> iterator() {
        return new DirectionalIterator(from, to, includeLast);
    }

    private final Coordinate from;
    private final Coordinate to;
    private final boolean includeLast;
}
