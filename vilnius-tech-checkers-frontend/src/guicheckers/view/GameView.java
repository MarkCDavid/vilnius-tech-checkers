package guicheckers.view;

import api.dto.Move;
import guicheckers.controls.GamePanel;
import guicheckers.controls.MoveHistory;
import guicheckers.controls.StatusBar;

import javax.swing.*;
import java.awt.*;

public class GameView extends AbstractView {

    public GameView(JFrame parent) {
        super(parent);
    }

    public GamePanel getGamePanel() {
        return (GamePanel)gamePanel;
    }

    public StatusBar getStatusBar() {
        return (StatusBar)statusBar;
    }

    public MoveHistory getMoveHistory() {
        return (MoveHistory) moveHistory;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public JButton getSurrenderButton() {
        return surrenderButton;
    }

    public JButton getDrawButton() {
        return drawButton;
    }

    @Override
    protected Container getRootContainer() {
        return mainPanel;
    }

    private JPanel mainPanel;
    private JPanel gamePanel;

    private JButton exitButton;
    private JButton surrenderButton;
    private JButton drawButton;

    private JLabel statusBar;
    private JList<Move> moveHistory;

    private void createUIComponents() {
        if(gamePanel == null)
            gamePanel = new GamePanel();

        if(moveHistory == null)
            moveHistory = new MoveHistory();

        if(statusBar == null)
            statusBar = new StatusBar();
    }

}