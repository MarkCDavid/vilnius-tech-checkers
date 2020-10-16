package backend.utils.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class BackwardsIterator<T> implements Iterator<T> {

    public BackwardsIterator(List<T> list) {
        this.iterator = list.listIterator(list.size());
    }
    @Override
    public boolean hasNext() {
        return iterator.hasPrevious();
    }

    @Override
    public T next() {
        if(!hasNext()) {
            throw new NoSuchElementException();
        }
        return iterator.previous();
    }

    private final ListIterator<T> iterator;
}
