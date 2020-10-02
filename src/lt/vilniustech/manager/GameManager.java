package lt.vilniustech.manager;

import lt.vilniustech.*;
import lt.vilniustech.events.EventEmitter;
import lt.vilniustech.events.EventSubscriber;
import lt.vilniustech.events.SubscriptionSupport;
import lt.vilniustech.manager.events.GameFinishedEvent;
import lt.vilniustech.manager.state.SimpleState;
import lt.vilniustech.manager.state.StateMachine;
import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.CheckersRuleset;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements CheckersManager, SubscriptionSupport {

    public boolean isFinished() {
        return winner != Side.NONE;
    }

    public Side getWinner() {
        return winner;
    }
    public void setWinner(Side winner) {
        this.winner = winner;
    }

    public Side getCurrentSide() {
        return currentSide;
    }

    public Board getBoard() {
        return board;
    }

    public StateMachine getStateMachine() { return stateMachine; }

    public GameManager(CheckersRuleset ruleset) {
        this.ruleset = ruleset;
        this.board = new Board(ruleset);
        this.eventEmitter = new EventEmitter();
        this.stateMachine = new StateMachine(new SimpleState());
        this.currentSide = ruleset.getFirstToMove();
        this.availableMoves = MoveCollectionsBuilder.getAllAvailableMoves(board, currentSide);
    }

    public void processMove(Move move) {
        if(isFinished())
            throw new GameFinishedException(getWinner());

        if(!legitimateMove(move))
            return;

        board.applyMove(move);

        winner = ruleset.processWinningConditions(currentSide, availableMoves, board.getSidePieces(Side.WHITE), board.getSidePieces(Side.BLACK));
        if (winner != Side.NONE) {
            eventEmitter.emit(new GameFinishedEvent(winner));
            return;
        }

        CaptureConstraints captureConstraints = ruleset.getCaptureConstraints(board, move);
        captureConstraints.setMultipleCaptures(stateMachine.isMultiCapture());

        currentSide = stateMachine.getNextSide(currentSide);
        availableMoves = captureConstraints.filterMoves(MoveCollectionsBuilder.getAllAvailableMoves(board, currentSide, stateMachine.isMultiCapture() ? stateMachine.getPerformedMoves() : null));
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

        for(Move move: availableMoves) {
            if(move.getFrom().equals(from))
                moves.add(move);
        }
        return moves;
    }

    private final StateMachine stateMachine;

    private List<Move> availableMoves;

    private Side winner = Side.NONE;
    private Side currentSide;

    private final CheckersRuleset ruleset;
    private final Board board;

    public void subscribe(EventSubscriber subscriber) {
        eventEmitter.subscribe(subscriber);
    }

    public void unsubscribe(EventSubscriber subscriber) {
        eventEmitter.subscribe(subscriber);
    }

    private final EventEmitter eventEmitter;
}
