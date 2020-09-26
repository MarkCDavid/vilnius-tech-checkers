package lt.vilniustech.guicheckers;

import lt.vilniustech.Coordinate;
import lt.vilniustech.manager.GameManager;
import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.english.EnglishCheckers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
