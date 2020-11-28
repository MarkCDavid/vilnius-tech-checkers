package guicheckers.view;

import api.dto.Ruleset;
import api.endpoints.GameLaunchService;
import backend.manager.GameManager;
import backend.moves.base.Move;
import guicheckers.controls.GamePanel;
import guicheckers.controls.MoveHistory;
import guicheckers.controls.StatusBar;

import javax.swing.*;
import java.awt.*;

public class GameView extends AbstractView {

    private final Ruleset ruleset;
    private final GameLaunchService gameLaunchService;

    public GameView(JFrame parent, Ruleset ruleset, GameLaunchService gameLaunchService) {
        super(parent);
        this.ruleset = ruleset;
        this.gameLaunchService = gameLaunchService;
    }

    private void createUIComponents() {
        gamePanel = new GamePanel(gameLaunchService.launch(ruleset));
        //moveHistory = new MoveHistory(getGamePanel().getGameManager().getBoard());
        //statusBar = new StatusBar();
    }

//    public StatusBar getStatusBar() {
//        return (StatusBar)this.statusBar;
//    }
//
//    public MoveHistory getMoveHistory() {
//        return (MoveHistory)this.moveHistory;
//    }

    public GamePanel getGamePanel() {
        return (GamePanel)this.gamePanel;
    }

//    public JButton getSurrenderButton() {
//        return surrenderButton;
//    }
//
//    public JButton getDrawButton() {
//        return drawButton;
//    }

    public JButton getExitButton() {
        return exitButton;
    }

    private JButton surrenderButton;
    private JButton drawButton;
    private JButton exitButton;

    private JPanel gamePanel;

    @Override
    protected Container getRootContainer() {
        return mainPanel;
    }
    private JPanel mainPanel;
}