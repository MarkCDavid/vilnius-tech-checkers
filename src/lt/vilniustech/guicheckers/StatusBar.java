package lt.vilniustech.guicheckers;

import lt.vilniustech.side.Side;

import javax.swing.*;

public class StatusBar extends JLabel {

    public void setCurrentSide(Side side) {
        currentSide = side;
        updateStatusBarText();
    }

    public void setWinner(Side side) {
        winner = side;
        updateStatusBarText();
    }
    private void updateFinalStatusBarText() {
        setText(String.format("The winner is: %s", winner.toString()));
        repaint();
    }

    private void updateStatusBarText() {
        if(winner != null) updateFinalStatusBarText();
        else setText(String.format("Current move: %s", currentSide.toString()));
        repaint();
    }

    private Side currentSide;
    private Side winner = null;
}
