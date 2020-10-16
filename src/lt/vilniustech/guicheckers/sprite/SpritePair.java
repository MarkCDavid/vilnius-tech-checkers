package lt.vilniustech.guicheckers.sprite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SpritePair<T extends Sprite> implements Iterable<T> {

    public SpritePair(T first, T second) {
        sprites = new ArrayList<>(2);
        sprites.add(first);
        sprites.add(second);
    }

    public T get(boolean first) {
        return first ? sprites.get(0) : sprites.get(1);
    }

    private final List<T> sprites;

    @Override
    public Iterator<T> iterator() {
        return sprites.iterator();
    }
}
