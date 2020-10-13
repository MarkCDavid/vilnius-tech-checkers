package lt.vilniustech.manager;

import lt.vilniustech.*;
import lt.vilniustech.events.EventEmitter;
import lt.vilniustech.events.EventSubscriber;
import lt.vilniustech.events.SubscriptionSupport;
import lt.vilniustech.manager.events.GameFinishedEvent;
import lt.vilniustech.manager.state2.SimpleState;
import lt.vilniustech.manager.state2.State;
import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.side.Side;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements CheckersManager, SubscriptionSupport {

    public boolean isFinished() {
        return winner != null;
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

    public GameManager(CheckersRuleset ruleset) {
        this.ruleset = ruleset;
        this.board = new Board(ruleset.getBoardSize());
        this.eventEmitter = new EventEmitter();
        this.currentSide = ruleset.getFirstToMove();
        this.movesBuilder = new AvailableMovesBuilder(board);

        this.state = new SimpleState(board, movesBuilder.buildAvailableMoves());
        this.availableMoves = state.getAvailableMoves();
    }

    public void processMove(Move move) {
        if(isFinished())
            throw new GameFinishedException(getWinner());

        if(!legitimateMove(move))
            return;

        move.apply(board);

        winner = ruleset.processWinningConditions(currentSide, availableMoves, board.getSidePieces(Side.WHITE), board.getSidePieces(Side.BLACK));
        if (isFinished()) {
            eventEmitter.emit(new GameFinishedEvent(winner));
            return;
        }

        state = state.process(movesBuilder.buildAvailableMoves(), move);
        availableMoves = state.getAvailableMoves();
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

    private List<Move> availableMoves;

    private Side winner = null;
    private Side currentSide;

    private final CheckersRuleset ruleset;
    private final Board board;

    public void subscribe(EventSubscriber subscriber) {
        eventEmitter.subscribe(subscriber);
    }

    public void unsubscribe(EventSubscriber subscriber) {
        eventEmitter.subscribe(subscriber);
    }

    private State state;
    private final EventEmitter eventEmitter;
    private final AvailableMovesBuilder movesBuilder;
}
