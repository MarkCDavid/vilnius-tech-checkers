package lt.vilniustech;

public class MoveUnavailableException extends RuntimeException {

    public MoveUnavailableException(Coordinate from, Coordinate to) {
        super(String.format("Given move from '%s' to '%s' is unavailable!", from, to));
        this.from = from;
        this.to = to;
    }

    private final Coordinate from;
    private final Coordinate to;

    public Coordinate getFrom() {
        return from;
    }

    public Coordinate getTo() {
        return to;
    }


}