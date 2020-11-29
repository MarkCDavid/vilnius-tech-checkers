package api.dto;

import java.util.Objects;

public class Move {

    public Move(Coordinate from, Coordinate to) {
        this.from = from;
        this.to = to;
    }

    public Coordinate getFrom() {
        return from;
    }

    public Coordinate getTo() {
        return to;
    }

    private final Coordinate from;
    private final Coordinate to;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var move = (Move) o;
        return from.equals(move.from) &&
                to.equals(move.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
