package lt.vilniustech.guicheckers;

import lt.vilniustech.Coordinate;
import lt.vilniustech.Side;
import lt.vilniustech.events.EventSubscriber;
import lt.vilniustech.guicheckers.events.CellClickListener;
import lt.vilniustech.guicheckers.events.MoveHistoryChangeListener;
import lt.vilniustech.manager.GameManager;
import lt.vilniustech.manager.events.GameFinishedEvent;
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

    private void createUIComponents() {
        GameManager manager = new GameManager(ruleset);

        manager.subscribe(new EventSubscriber<>(GameFinishedEvent.class) {
            @Override
            protected void receive(GameFinishedEvent event) {
                processGameEnd(event.getWinner());
            }
        });

        GamePanel gamePanel = setGamePanel(new GamePanel(manager));
        MoveHistory moveHistory = setMoveHistory(new MoveHistory(gamePanel.getGameManager().getBoard()));
        StatusBar statusBar = setStatusBar(new StatusBar());

        moveHistory.addHistoryChangeListener(getMoveHistoryChangeListener(gamePanel, moveHistory));
        gamePanel.addCellClickListener(getCellClickListener(gamePanel, moveHistory, statusBar));
    }

    private CellClickListener getCellClickListener(GamePanel gamePanel, MoveHistory moveHistory, StatusBar statusBar) {
        return coordinate -> {
            if (!moveHistory.isPresent()) return;

            List<Move> selectedMoves = gamePanel.getSelectedMoves();
            boolean selectMove = selectedMoves.size() == 0;

            if (selectMove) {
                selectMove(gamePanel, coordinate);
            } else {
                executeMove(gamePanel, moveHistory, coordinate, selectedMoves);
            }
            gamePanel.repaint();
            statusBar.setCurrentSide(gamePanel.getGameManager().getCurrentSide());
            moveHistory.setSelectedIndex(moveHistory.getMaxSelectionIndex());
        };
    }

    private void selectMove(GamePanel gamePanel, Coordinate from) {
        gamePanel.setSelectedMoves(gamePanel.getGameManager().getAvailableMoves(from));
    }

    private void executeMove(GamePanel gamePanel, MoveHistory moveHistory, Coordinate to, List<Move> selectedMoves) {
        for (Move availableMove : selectedMoves) {

            if (!availableMove.getTo().equals(to))
                continue;

            moveHistory.addMove(availableMove);

            gamePanel.getGameManager().processMove(availableMove);

            if(gameFinished)
                break;

//            getMoveHistory().setEnabled(!gamePanel.getGameManager().getStateMachine().isMultiCapture());
//            drawButton.setEnabled(!gamePanel.getGameManager().getStateMachine().isMultiCapture());
//            surrenderButton.setEnabled(!gamePanel.getGameManager().getStateMachine().isMultiCapture());

            break;
        }
        selectedMoves.clear();
    }

    private MoveHistoryChangeListener getMoveHistoryChangeListener(GamePanel gamePanel, MoveHistory moveHistory) {
        return move -> {
            gamePanel.setSelectedMoves(new ArrayList<>());
            gamePanel.setDrawHighlights(moveHistory.isPresent());
            gamePanel.repaint();
        };
    }

    private boolean gameFinished = false;
    private void processGameEnd(Side winner) {
        gameFinished = true;
        getStatusBar().setWinner(winner);
        getGamePanel().repaint();
        getGamePanel().setEnabled(false);
        getMoveHistory().setEnabled(true);
        drawButton.setEnabled(false);
        surrenderButton.setEnabled(false);
    }

    private JButton surrenderButton;
    private JButton drawButton;
    private JButton exitButton;

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
