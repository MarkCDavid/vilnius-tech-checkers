package guicheckers.controller;

import api.services.RulesetService;
import backend.Coordinate;
import backend.events.EventSubscriber;
import backend.manager.events.event.GameFinishedEvent;
import backend.manager.events.event.MoveProcessedEvent;
import backend.moves.base.Move;
import backend.side.Side;
import guicheckers.events.CellClickListener;
import guicheckers.events.MoveHistoryChangeListener;
import guicheckers.view.GameView;
import guicheckers.view.MainMenuView;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GameViewController extends AbstractController<GameView> {

    public GameViewController(GameView view) {
        super(view);

        getView().getExitButton().addActionListener(exitButtonAction());
        getView().getDrawButton().addActionListener(drawButtonAction());
        getView().getSurrenderButton().addActionListener(surrenderButtonAction());
        getView().getMoveHistory().addHistoryChangeListener(getMoveHistoryChangeListener());
        getView().getGamePanel().addCellClickListener(getCellClickListener());
        getView().getGamePanel().getGameManager().subscribe(new EventSubscriber<>(GameFinishedEvent.class) {
            @Override
            protected void receive(GameFinishedEvent event) {
                processGameEnd(event.getWinner());
            }
        });
        getView().getGamePanel().getGameManager().subscribe(new EventSubscriber<>(MoveProcessedEvent.class) {
            @Override
            protected void receive(MoveProcessedEvent event) {
                getView().getMoveHistory().addMove(event.getMove());
            }
        });
    }

    private ActionListener surrenderButtonAction() {
        return e -> {
            var surrenderingSide = getView().getGamePanel().getGameManager().getCurrentSide();
            var nextPlayingSide = surrenderingSide.getNext();
            var playingSides = getView().getGamePanel().getGameManager().getPlayingSides();

            for(Side side: playingSides) {
                if(side.getNext().equals(surrenderingSide)) {
                    side.setNext(side.getNext().getNext());
                }
            }
            playingSides.remove(surrenderingSide);


            if(playingSides.size() == 1) {
                getView().getGamePanel().getGameManager().setWinner(playingSides.get(0));
                processGameEnd(playingSides.get(0));
            }
            else {
                getView().getGamePanel().getGameManager().switchSide(nextPlayingSide);
                getView().getGamePanel().repaint();
            }
        };
    }

    private boolean gameFinished = false;
    private void processGameEnd(Side winner) {
        gameFinished = true;
        getView().getStatusBar().setWinner(winner);
        getView().getGamePanel().repaint();
        getView().getGamePanel().setEnabled(false);
        getView().getMoveHistory().setEnabled(true);
        getView().getDrawButton().setEnabled(false);
        getView().getSurrenderButton().setEnabled(false);
    }

    public ActionListener exitButtonAction() {
        return e -> {
            var view = new MainMenuView(getView().getFrame());
            var controller = new MainMenuController(view, new RulesetService());
            view.render();
        };
    }

    public ActionListener drawButtonAction() {
        return e -> {
            getView().getGamePanel().getGameManager().setWinner(Side.DRAW);
            processGameEnd(Side.DRAW);
        };
    }



    private CellClickListener getCellClickListener() {
        return coordinate -> {
            if (!getView().getMoveHistory().isPresent()) return;

            List<Move> selectedMoves = getView().getGamePanel().getSelectedMoves();
            boolean selectMove = selectedMoves.isEmpty();

            if (selectMove) {
                selectMove(coordinate);
            } else {
                executeMove(coordinate, selectedMoves);
            }
            getView().getGamePanel().repaint();
            getView().getStatusBar().setCurrentSide(getView().getGamePanel().getGameManager().getCurrentSide());
            getView().getMoveHistory().setSelectedIndex(getView().getMoveHistory().getMaxSelectionIndex());
        };
    }

    private void selectMove(Coordinate from) {
        getView().getGamePanel().setSelectedMoves(getView().getGamePanel().getGameManager().getAvailableMoves(from));
    }

    private void executeMove(Coordinate to, List<Move> selectedMoves) {
        for (Move availableMove : selectedMoves) {

            if (!availableMove.getTo().equals(to))
                continue;

            getView().getGamePanel().getGameManager().processMove(availableMove);

            if(gameFinished)
                break;

            break;
        }
        selectedMoves.clear();
    }

    private MoveHistoryChangeListener getMoveHistoryChangeListener() {
        return move -> {
            getView().getGamePanel().setSelectedMoves(new ArrayList<>());
            getView().getGamePanel().setDrawHighlights(getView().getMoveHistory().isPresent());
            getView().getGamePanel().repaint();
        };
    }
}
