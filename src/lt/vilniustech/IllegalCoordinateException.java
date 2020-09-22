package lt.vilniustech;

public class IllegalCoordinateException extends RuntimeException {

    public IllegalCoordinateException(String coordinate) {
        super(coordinate);
    }
}
