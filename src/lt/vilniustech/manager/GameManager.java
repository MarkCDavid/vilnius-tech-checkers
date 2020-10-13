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
        return this.state.getCurrentSide();
    }

    public Board getBoard() {
        return board;
    }

    public GameManager(CheckersRuleset ruleset) {
        this.ruleset = ruleset;
        this.playingSides = ruleset.getPlayingSides();

        this.board = new Board(ruleset.getBoardSize());
        for(Side side: this.playingSides) {
            side.fillBoard(board);
        }

        this.eventEmitter = new EventEmitter();
        this.state = new SimpleState(board, ruleset, this.playingSides.get(0));

        this.processedMoves = new ArrayList<>();
    }

    public void processMove(Move move) {
        if(isFinished())
            throw new GameFinishedException(getWinner());

        if(!legitimateMove(move))
            return; // Illegitimate Move Exception ??

        move.apply(board);
        state = state.process(move);
        processedMoves.add(state.getFinalizedMove());

        winner = ruleset.processWinningConditions(board, state.getAvailableMoves(), playingSides, state.getCurrentSide());
        if (isFinished())
            eventEmitter.emit(new GameFinishedEvent(winner));
    }

    private boolean legitimateMove(Move move) {
        return state.getAvailableMoves().contains(move);
    }


    public List<Move> getAvailableMoves() {
        return isFinished() ? new ArrayList<>() : new ArrayList<>(state.getAvailableMoves());
    }

    public List<Move> getAvailableMoves(Coordinate from) {
        List<Move> moves = new ArrayList<>();
        if(isFinished()) return moves;

        for(Move move: state.getAvailableMoves()) {
            if(move.getFrom().equals(from))
                moves.add(move);
        }
        return moves;
    }

    private Side winner = null;

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

    private final List<Side> playingSides;

    public Move getProcessedMove() {
        return state.getFinalizedMove();
    }

    private final List<Move> processedMoves;
}
