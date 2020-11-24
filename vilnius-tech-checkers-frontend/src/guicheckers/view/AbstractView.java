package guicheckers.view;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractView {

    private final JFrame frame;

    public JFrame getFrame() {
        return frame;
    }

    public AbstractView(JFrame parent) {
        this.frame = parent;
    }

    public void render() {
        frame.setContentPane(getRootContainer());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    protected abstract Container getRootContainer();
}
