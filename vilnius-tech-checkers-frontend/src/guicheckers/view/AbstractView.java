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
        SwingUtilities.invokeLater(() -> {
                    frame.setContentPane(getRootContainer());
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                }
        );
    }

    public <T extends Component> T switchComponent(T _old, T _new){
        var parent = _old.getParent();
        parent.remove(_old);
        parent.add(_new);
        parent.invalidate();
        return _new;
    }

    protected abstract Container getRootContainer();
}
