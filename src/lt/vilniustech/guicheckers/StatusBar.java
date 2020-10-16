package lt.vilniustech.guicheckers;

import javax.swing.*;

public class StatusBar extends JLabel {

    public void setCurrentSide(String side) {
        currentSide = side;
        updateStatusBarText();
    }

    public void setWinner(String  side) {
        winner = side;
        updateStatusBarText();
    }
    private void updateFinalStatusBarText() {
        setText(String.format("The winner is: %s", winner));
        repaint();
    }

    private void updateStatusBarText() {
        if(winner != null) updateFinalStatusBarText();
        else setText(String.format("Current move: %s", currentSide));
        repaint();
    }

    private String currentSide;
    private String winner = null;
}
