package lt.vilniustech.manager.exceptions;

public interface OnManagerException {
    void apply(Exception exception);
}
