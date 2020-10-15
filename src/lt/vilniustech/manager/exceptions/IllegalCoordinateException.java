package lt.vilniustech.manager.exceptions;

public class IllegalCoordinateException extends RuntimeException {

    public IllegalCoordinateException(String coordinate) {
        super(String.format("The given coordinate '%s' is not valid!", coordinate));
    }
}
