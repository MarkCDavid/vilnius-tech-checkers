package guicheckers.controller;

import guicheckers.view.AbstractView;

public abstract class AbstractController<T extends AbstractView> {

    public T getView() {
        return view;
    }

    private final T view;

    public AbstractController(T view) {
        this.view = view;
    }

    public void render() {
        this.view.render();
    }
}
