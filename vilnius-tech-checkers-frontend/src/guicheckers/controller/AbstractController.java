package guicheckers.controller;

import guicheckers.view.AbstractView;

public class AbstractController<T extends AbstractView> {

    public T getView() {
        return view;
    }

    private final T view;

    public AbstractController(T view) {
        this.view = view;
    }
}
