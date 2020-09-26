package lt.vilniustech.guicheckers;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.manager.GameManager;
import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.english.EnglishCheckers;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckersGUIForm {

    JPanel mainPanel;
    private JPanel gamePanel;
    private JLabel playerLabel;
    private JList<Move> madeMoves;

    private Move previousSelected = null;

    public CheckersGUIForm() throws IOException {



        madeMoves.addListSelectionListener(listSelectionEvent -> {
            GUIGame game = (GUIGame) gamePanel;
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

    private static final int SCREEN_SIZE = 800;

    private void createUIComponents() throws IOException {
        gamePanel = new GUIGame(new GameManager(new EnglishCheckers()));

        GUIGame game = (GUIGame) gamePanel;

        playerLabel = new JLabel();
        playerLabel.setText(game.getGameManager().getCurrentSide().toString());

        movesModel = new DefaultListModel<>();
        madeMoves = new JList<>(movesModel);

        game.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {

                if(previousSelected != null && movesModel.get(movesModel.getSize() - 1) != previousSelected)
                    return;

                int row = e.getY() / game.getCellSize();
                int column = e.getX() / game.getCellSize();

                Coordinate coordinate = new Coordinate(column, row);
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
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) { }

            @Override
            public void mouseClicked(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) { }
        });
    }
}
