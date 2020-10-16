package backend.manager.exceptions;

public interface OnManagerException {
    void apply(Exception exception);
}
