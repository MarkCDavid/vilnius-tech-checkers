package lt.vilniustech.guicheckers;

import javax.swing.*;
import java.io.IOException;

public class GUIClient {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Checkers");
        CheckersGUIForm form = new CheckersGUIForm();
        frame.setContentPane(form.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
