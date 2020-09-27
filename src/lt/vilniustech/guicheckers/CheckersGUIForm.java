package lt.vilniustech.guicheckers;

import lt.vilniustech.Board;
import lt.vilniustech.manager.GameManager;
import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.english.EnglishCheckers;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CheckersGUIForm {

    JPanel mainPanel;

    public CheckersGUIForm() {


    }


    private void createUIComponents() {
        GamePanel gamePanel = setGamePanel(new GamePanel(new GameManager(new EnglishCheckers())));

        playerLabel = new JLabel(gamePanel.getGameManager().getBoard().getRuleset().getFirstToMove().toString());

        moveHistory = new MoveHistory(gamePanel.getGameManager().getBoard());
        moveHistory.addHistoryChangeListener(move -> {
            gamePanel.setSelectedMoves(new ArrayList<>());
            gamePanel.setDrawHighlights(moveHistory.isPresent());
            gamePanel.repaint();
        });

        gamePanel.addCellClickListener(coordinate -> {
            if(!moveHistory.isPresent())
                return;

            GameManager gameManager = gamePanel.getGameManager();
            List<Move> selectedMoves = gamePanel.getSelectedMoves();

            if(selectedMoves.size() == 0) {
                gamePanel.setSelectedMoves(gameManager.getAvailableMoves(coordinate));
            }
            else{
                for(Move availableMove: selectedMoves) {
                    if(availableMove.getTo().equals(coordinate)) {
                        gameManager.performMove(availableMove);
                        moveHistory.addMove(availableMove);
                        gamePanel.setSelectedMoves(new ArrayList<>());
                        break;
                    }
                }
                selectedMoves.clear();
            }
            gamePanel.repaint();

            playerLabel.setText(gamePanel.getGameManager().getCurrentSide().toString());
            moveHistory.setSelectedIndex(moveHistory.getMaxSelectionIndex());
        });

    }

    private GamePanel getGamePanel() {
        return (GamePanel) this.gamePanel;
    }

    private GamePanel setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        return getGamePanel();
    }

    private JPanel gamePanel;
    private JLabel playerLabel;
    private MoveHistory moveHistory;

}
