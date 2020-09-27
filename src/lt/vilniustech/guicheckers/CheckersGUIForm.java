package lt.vilniustech.guicheckers;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.manager.GameManager;
import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.english.EnglishCheckers;
import lt.vilniustech.rulesets.turkish.TurkishCheckers;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckersGUIForm {

    JPanel mainPanel;
    private JLabel playerLabel;
    private JList<Move> madeMoves;

    private Move previousSelected = null;

    public CheckersGUIForm() throws IOException {

        madeMoves.addListSelectionListener(listSelectionEvent -> {
            GamePanel game = (GamePanel) gamePanel;
            Board board = game.getGameManager().getBoard();
            game.setSelectedMoves(new ArrayList<>());
            Move move = madeMoves.getSelectedValue();
            if(previousSelected == null)
                previousSelected = movesModel.get(movesModel.getSize() - 1);
            int to = movesModel.indexOf(move);
            int from = movesModel.indexOf(previousSelected);



            if(to > from) {
                for(int i = from; i <= to; i++) {
                    Move m = movesModel.get(i);
                    m.apply(board);
                }
            }
            if(from > to) {
                for(int i = from; i > to; i--) {
                    Move m = movesModel.get(i);
                    m.revert(board);
                }
            }
            previousSelected = move;

            game.repaint();
        });
    }

    private DefaultListModel<Move> movesModel;

    private void createUIComponents() {
        gamePanel = new GamePanel(new GameManager(new EnglishCheckers()));

        GamePanel game = getGamePanel();

        playerLabel = new JLabel();
        playerLabel.setText(game.getGameManager().getCurrentSide().toString());

        movesModel = new DefaultListModel<>();
        madeMoves = new JList<>(movesModel);

        game.addCellClickListener(coordinate -> {
                if(previousSelected != null && movesModel.get(movesModel.getSize() - 1) != previousSelected)
                    return;

                GameManager gameManager = game.getGameManager();
                List<Move> selectedMoves = game.getSelectedMoves();

                if(selectedMoves.size() == 0) {
                    game.setSelectedMoves(gameManager.getAvailableMoves(coordinate));
                }
                else{
                    for(Move availableMove: selectedMoves) {
                        if(availableMove.getTo().equals(coordinate)) {
                            gameManager.performMove(availableMove);
                            movesModel.addElement(availableMove);
                            game.setSelectedMoves(new ArrayList<>());
                            break;
                        }
                    }
                    selectedMoves.clear();
                }
                game.repaint();

                playerLabel.setText(game.getGameManager().getCurrentSide().toString());
                madeMoves.setSelectedIndex(madeMoves.getMaxSelectionIndex());
        });

    }



    private JPanel gamePanel;
    private GamePanel getGamePanel() {
        return (GamePanel) gamePanel;
    }

}
