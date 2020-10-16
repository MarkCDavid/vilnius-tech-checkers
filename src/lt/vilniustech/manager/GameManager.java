package lt.vilniustech.manager;

import lt.vilniustech.*;
import lt.vilniustech.events.EventEmitter;
import lt.vilniustech.events.EventSubscriber;
import lt.vilniustech.events.SubscriptionSupport;
import lt.vilniustech.manager.events.GameFinishedEvent;
import lt.vilniustech.manager.events.MoveProcessedEvent;
import lt.vilniustech.manager.exceptions.GameFinishedException;
import lt.vilniustech.manager.exceptions.IllegitimateMoveException;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.moves.finalization.FinalizationArguments;
import lt.vilniustech.moves.finalization.FinalizationArgumentsBuilder;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.side.Side;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameManager implements SubscriptionSupport {

    public boolean isFinished() {
        return playingSides.size() < 2;
    }

    public Side getWinner() {
        return playingSides.isEmpty() ? Side.DRAW : playingSides.get(0);
    }
    public void setWinner(Side winner) {
        this.playingSides = new ArrayList<>();
        this.playingSides.add(winner);
    }

    public Side getCurrentSide() {
        return this.currentSide;
    }

    public List<Side> getPlayingSides() {
        return playingSides;
    }

    public Board getBoard() {
        return board;
    }

    public GameManager(CheckersRuleset ruleset) {
        this.moveHistory = new MoveHistory();
        this.eventEmitter = new EventEmitter();

        this.ruleset = ruleset;

        this.playingSides = ruleset.getPlayingSides();
        this.board = new Board(ruleset.getBoardSize());

        for(Side side: this.playingSides) {
            side.fillBoard(board);
        }

        this.currentSide = this.playingSides.get(0);

        this.availableMovesBuilder = new AvailableMovesBuilder(board, moveHistory, ruleset.getMoveFactory());
        this.finalizationArgumentsBuilder = new FinalizationArgumentsBuilder(board, ruleset, moveHistory, this.availableMovesBuilder);

        this.availableMoves = availableMovesBuilder.buildAvailableMoves(currentSide);
    }

    public void processMove(Move move) {
        if(isFinished())
            throw new GameFinishedException(getWinner());

        if(!legitimateMove(move))
            throw new IllegitimateMoveException(move);

        FinalizationArguments arguments = finalizationArgumentsBuilder.build(getCurrentSide(), move);
        move = move.finalizeMove(board, moveHistory, arguments);
        move.apply(board);
        moveHistory.add(move);
        eventEmitter.emit(new MoveProcessedEvent(move));

        if(arguments.isPromote())
            board.putPiece(move.getTo(), board.popPiece(move.getTo()).promote());

        if(arguments.isSwitchSide())
            currentSide = currentSide.getNext();

        processSideSwitch(move, !arguments.isSwitchSide());

        Side next = ruleset.processWinningConditions(board, availableMoves, playingSides, currentSide);
        if(!Objects.equals(currentSide, next)) {
            switchSide(next);
        }

        if (isFinished())
            eventEmitter.emit(new GameFinishedEvent(getWinner()));
    }

    public void switchSide(Side side) {
        currentSide = side;
        processSideSwitch(null, false);
    }

    private void processSideSwitch(Move move, boolean multiCapture) {
        CaptureConstraints captureConstraints = ruleset.getCaptureConstraints(board, moveHistory, move);
        captureConstraints.setMultiCapture(multiCapture);
        availableMoves = captureConstraints.filterMoves(availableMovesBuilder.buildAvailableMoves(currentSide));
    }

    private boolean legitimateMove(Move move) {
        return availableMoves.contains(move);
    }

    public List<Move> getAvailableMoves() {
        return isFinished() ? new ArrayList<>() : new ArrayList<>(availableMoves);
    }

    public List<Move> getAvailableMoves(Coordinate from) {
        List<Move> moves = new ArrayList<>();
        if(isFinished()) return moves;

        for(Move move: getAvailableMoves()) {
            if(move.getFrom().equals(from))
                moves.add(move);
        }
        return moves;
    }

    private final CheckersRuleset ruleset;
    private final Board board;

    private Side currentSide;
    private List<Side> playingSides;

    private final MoveHistory moveHistory;

    private List<Move> availableMoves;

    private final AvailableMovesBuilder availableMovesBuilder;
    private final FinalizationArgumentsBuilder finalizationArgumentsBuilder;

    public void subscribe(EventSubscriber subscriber) {
        eventEmitter.subscribe(subscriber);
    }
    public void unsubscribe(EventSubscriber subscriber) {
        eventEmitter.subscribe(subscriber);
    }
    private final EventEmitter eventEmitter;
}
