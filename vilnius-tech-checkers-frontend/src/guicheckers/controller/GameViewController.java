package guicheckers.controller;

import api.dto.*;
import api.endpoints.GameDataService;
import api.endpoints.GameInteractionService;
import api.services.GameService;
import api.services.RulesetService;
import guicheckers.events.CellClickListener;
import guicheckers.view.GameView;
import guicheckers.view.MainMenuView;

import java.awt.event.ActionListener;
import java.util.List;

public class GameViewController extends AbstractController<GameView> {


    public GameViewController(
            GameView view,
            GameLaunchData launchData,
            GameDataService dataService,
            GameInteractionService interactionService
    ) {
        super(view);

        this.launchData = launchData;
        this.dataService = dataService;
        this.interactionService = interactionService;

        dataService.subscribeOnChangeListener(launchData.getGame(), () -> {
            System.out.println("CHANGE HAPPENED");
            updatePanelData();
        });

        getView().getGamePanel().addCellClickListener(getCellClickListener());
        updatePanelData();



        //getView().getExitButton().addActionListener(exitButtonAction());
//        getView().getDrawButton().addActionListener(drawButtonAction());
//        getView().getSurrenderButton().addActionListener(surrenderButtonAction());
       // getView().getMoveHistory().addHistoryChangeListener(getMoveHistoryChangeListener());

//        getView().getGamePanel().getGameManager().subscribe(new EventSubscriber<>(GameFinishedEvent.class) {
//            @Override
//            protected void receive(GameFinishedEvent event) {
//                processGameEnd(event.getWinner());
//            }
//        });
//        getView().getGamePanel().getGameManager().subscribe(new EventSubscriber<>(MoveProcessedEvent.class) {
//            @Override
//            protected void receive(MoveProcessedEvent event) {
//                getView().getMoveHistory().addMove(event.getMove());
//            }
//        });

    }

    private boolean gameFinished = false;
    private void processGameEnd(Side winner) {
        gameFinished = true;
//        getView().getStatusBar().setWinner(winner);
//        getView().getGamePanel().repaint();
//        getView().getGamePanel().setEnabled(false);
//        getView().getMoveHistory().setEnabled(true);
//        getView().getDrawButton().setEnabled(false);
//        getView().getSurrenderButton().setEnabled(false);
    }

    public ActionListener exitButtonAction() {
        return e -> {
            var view = new MainMenuView(getView().getFrame());
            var controller = new MainMenuController(view, new RulesetService(), (GameService)dataService);
            view.render();
        };
    }

    private CellClickListener getCellClickListener() {
        return coordinate -> {
            if(selectedInteractable == null) {
                var interactables = interactionService.getInteractable(launchData.getGame(), getCurrentPlayer());
                if(interactables.contains(coordinate)) {
                    selectedInteractable = coordinate;
                    updatePanelData();
                }
            } else {
                var move = new Move(selectedInteractable, coordinate);
                var moves = interactionService.getMoves(launchData.getGame(), getCurrentPlayer(), selectedInteractable);

                if(moves.contains(move))
                    interactionService.applyMove(launchData.getGame(), getCurrentPlayer(), move);

                selectedInteractable = null;
                updatePanelData();
            }
//            if (!getView().getMoveHistory().isPresent()) return;

//            List<Move> selectedMoves = getView().getGamePanel().getSelectedMoves();
//            boolean selectMove = selectedMoves.isEmpty();
//
//            if (selectMove) {
//                selectMove(coordinate);
//            } else {
//                executeMove(coordinate, selectedMoves);
//            }
//            getView().getGamePanel().repaint();
//            getView().getStatusBar().setCurrentSide(getView().getGamePanel().getGameManager().getCurrentSide());
//            getView().getMoveHistory().setSelectedIndex(getView().getMoveHistory().getMaxSelectionIndex());
        };
    }
//
//    private void selectMove(Coordinate from) {
//        getView().getGamePanel().setSelectedMoves(getView().getGamePanel().getGameManager().getAvailableMoves(from));
//    }

    private void executeMove(Coordinate to, List<Move> selectedMoves) {
        for (Move availableMove : selectedMoves) {

            if (!availableMove.getTo().equals(to))
                continue;

//            getView().getGamePanel().getGameManager().processMove(availableMove);

            if(gameFinished)
                break;

            break;
        }
        selectedMoves.clear();
    }
//
//    private MoveHistoryChangeListener getMoveHistoryChangeListener() {
//        return move -> {
//            getView().getGamePanel().setSelectedMoves(new ArrayList<>());
//            getView().getGamePanel().setDrawHighlights(getView().getMoveHistory().isPresent());
//            getView().getGamePanel().repaint();
//        };
//    }

    private void updatePanelData() {
        var panel = getView().getGamePanel();

        panel.invalidateData();
        panel.setBoardSideLength(dataService.getBoardData(launchData.getGame()).getSideLength());
        panel.setCells(dataService.getCells(launchData.getGame()));
        if(selectedInteractable == null)
            panel.setInteractables(interactionService.getInteractable(launchData.getGame(), getCurrentPlayer()));
        else
            panel.setMoves(interactionService.getMoves(launchData.getGame(), getCurrentPlayer(), selectedInteractable));

        panel.repaint();
    }

    private Coordinate selectedInteractable;

    private PlayerToken getCurrentPlayer() {
        var currentSide = dataService.getCurrentSide(launchData.getGame());
        return launchData.getPlayers().getOrDefault(currentSide, null);
    }

    private final GameLaunchData launchData;
    private final GameDataService dataService;
    private final GameInteractionService interactionService;

}
