package lt.vilniustech.guicheckers;

import lt.vilniustech.Side;

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
        switch (winner) {
            case BLACK, WHITE -> setText(String.format("The winner is: %s", winner.toString()));
            case DRAW -> setText("The match finished in a draw!");
            default -> throw new IllegalStateException(winner.getClass().getName());
        }
        repaint();
    }

    private void updateStatusBarText() {
        if(winner != Side.NONE) updateFinalStatusBarText();
        else setText(String.format("Current move: %s", currentSide.toString()));
        repaint();
    }

    private Side currentSide;
    private Side winner = Side.NONE;
}
