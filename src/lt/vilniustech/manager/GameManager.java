package lt.vilniustech.manager;

import lt.vilniustech.*;
import lt.vilniustech.events.EventEmitter;
import lt.vilniustech.events.EventSubscriber;
import lt.vilniustech.events.SubscriptionSupport;
import lt.vilniustech.manager.events.GameFinishedEvent;
import lt.vilniustech.manager.events.MoveProcessedEvent;
import lt.vilniustech.manager.state2.SimpleState;
import lt.vilniustech.manager.state2.AbstractState;
import lt.vilniustech.moves.base.AbstractMove;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.moves.finalization.FinalizationArguments;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.side.Side;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements CheckersManager, MoveHistorySupport, SubscriptionSupport {

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
        this.moveHistory = new ArrayList<>();
    }

    public void processMove(AbstractMove move) {
        if(isFinished())
            throw new GameFinishedException(getWinner());

        if(!legitimateMove(move))
            return; // Illegitimate Move Exception ??

        FinalizationArguments arguments = FinalizationArguments.build(board, getCurrentSide(), move);
        move = move.finalizeMove(board, arguments);
        move.apply(board);

        moveHistory.add(move);
        eventEmitter.emit(new MoveProcessedEvent(move));

        winner = ruleset.processWinningConditions(board, state.getAvailableMoves(), playingSides, state.getCurrentSide());
        if (isFinished())
            eventEmitter.emit(new GameFinishedEvent(winner));
    }

    private boolean legitimateMove(AbstractMove move) {
        return state.getAvailableMoves().contains(move);
    }


    public List<AbstractMove> getAvailableMoves() {
        return isFinished() ? new ArrayList<>() : new ArrayList<>(state.getAvailableMoves());
    }

    public List<AbstractMove> getAvailableMoves(Coordinate from) {
        List<AbstractMove> moves = new ArrayList<>();
        if(isFinished()) return moves;

        for(AbstractMove move: state.getAvailableMoves()) {
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

    private AbstractState state;

    private final List<Side> playingSides;
    private final EventEmitter eventEmitter;

    @Override
    public List<Move> getMoveHistory() {
        return moveHistory;
    }

    private final List<Move> moveHistory;
}
