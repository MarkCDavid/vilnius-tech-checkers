package lt.vilniustech.guicheckers;

import lt.vilniustech.Side;
import lt.vilniustech.manager.GameManager;
import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.CheckersRuleset;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GameForm {

    JFrame parent;
    JPanel mainPanel;
    CheckersRuleset ruleset;

    public GameForm(JFrame parent, CheckersRuleset ruleset) {
        this.parent = parent;
        this.ruleset = ruleset;

        exitButton.addActionListener(e -> MainMenuForm.show(parent));
        drawButton.addActionListener(e -> {
            Side winner = Side.DRAW;
            getGamePanel().getGameManager().setWinner(winner);
            processGameEnd(winner);
        });
        surrenderButton.addActionListener(e -> {
            Side winner = Side.opposite(getGamePanel().getGameManager().getCurrentSide());
            getGamePanel().getGameManager().setWinner(winner);
            processGameEnd(winner);
        });
    }

    public void processGameEnd(Side winner) {
        getStatusBar().setWinner(winner);
        getGamePanel().repaint();
        getGamePanel().setEnabled(false);
        drawButton.setEnabled(false);
        surrenderButton.setEnabled(false);
    }

    private void createUIComponents() {
        GamePanel gamePanel = setGamePanel(new GamePanel(new GameManager(ruleset)));
        MoveHistory moveHistory = setMoveHistory(new MoveHistory(gamePanel.getGameManager().getBoard()));
        StatusBar statusBar = setStatusBar(new StatusBar());

        moveHistory.addHistoryChangeListener(move -> {
            gamePanel.setSelectedMoves(new ArrayList<>());
            gamePanel.setDrawHighlights(moveHistory.isPresent());
            gamePanel.repaint();
        });

        gamePanel.addCellClickListener(coordinate -> {
            if(!moveHistory.isPresent())
                return;

            List<Move> selectedMoves = gamePanel.getSelectedMoves();

            if(selectedMoves.size() == 0) {
                gamePanel.setSelectedMoves(gamePanel.getGameManager().getAvailableMoves(coordinate));
            }
            else{
                for(Move availableMove: selectedMoves) {
                    if(availableMove.getTo().equals(coordinate)) {
                        moveHistory.addMove(availableMove);
                        boolean isFinished = gamePanel.getGameManager().performMove(availableMove);
                        if(isFinished)
                            processGameEnd(gamePanel.getGameManager().getWinner());

                        break;
                    }
                }
                selectedMoves.clear();
            }
            gamePanel.repaint();
            statusBar.setCurrentSide(gamePanel.getGameManager().getCurrentSide());
            moveHistory.setSelectedIndex(moveHistory.getMaxSelectionIndex());
        });


    }

    private JPanel gamePanel;
    private GamePanel getGamePanel() {
        return (GamePanel) this.gamePanel;
    }
    private GamePanel setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        return getGamePanel();
    }


    private JList<Move> moveHistory;
    private MoveHistory getMoveHistory() {
        return (MoveHistory) this.moveHistory;
    }
    private MoveHistory setMoveHistory(MoveHistory moveHistory) {
        this.moveHistory = moveHistory;
        return getMoveHistory();
    }


    private JLabel statusBar;
    private JButton surrenderButton;
    private JButton drawButton;
    private JButton exitButton;

    private StatusBar getStatusBar() {
        return (StatusBar) this.statusBar;
    }
    private StatusBar setStatusBar(StatusBar statusBar) {
        this.statusBar = statusBar;
        return getStatusBar();
    }

    public static void show(JFrame frame, CheckersRuleset ruleset) {
        GameForm form = new GameForm(frame, ruleset);
        frame.setContentPane(form.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
