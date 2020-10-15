package lt.vilniustech.manager;

import lt.vilniustech.*;
import lt.vilniustech.events.EventEmitter;
import lt.vilniustech.events.EventSubscriber;
import lt.vilniustech.events.SubscriptionSupport;
import lt.vilniustech.manager.events.GameFinishedEvent;
import lt.vilniustech.manager.events.MoveProcessedEvent;
import lt.vilniustech.manager.exceptions.GameFinishedException;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.moves.finalization.FinalizationArguments;
import lt.vilniustech.moves.finalization.FinalizationArgumentsBuilder;
import lt.vilniustech.rulesets.CaptureConstraints;
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
        return this.currentSide;
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
        this.currentSide = this.playingSides.get(0);
        this.moveHistory = new MoveHistory();
        this.availableMovesBuilder = new AvailableMovesBuilder(board, ruleset, moveHistory  );
        this.finalizationArgumentsBuilder = new FinalizationArgumentsBuilder(board, ruleset, moveHistory);

        this.availableMoves = availableMovesBuilder.buildAvailableMoves(currentSide);
    }

    public void processMove(Move move) {
        if(isFinished())
            throw new GameFinishedException(getWinner());

        if(!legitimateMove(move))
            return; // Illegitimate Move Exception ??

        FinalizationArguments arguments = finalizationArgumentsBuilder.build(getCurrentSide(), move);
        move = move.finalizeMove(board, moveHistory, arguments);
        move.apply(board);
        moveHistory.add(move);
        eventEmitter.emit(new MoveProcessedEvent(move));

        if(arguments.isPromote())
            board.putPiece(move.getTo(), board.popPiece(move.getTo()).promote());

        if(arguments.isSwitchSide())
            currentSide = currentSide.getNext();

        CaptureConstraints captureConstraints = ruleset.getCaptureConstraints(board, moveHistory, move);
        captureConstraints.setMultiCapture(!arguments.isSwitchSide());
        availableMoves = captureConstraints.filterMoves(availableMovesBuilder.buildAvailableMoves(currentSide));

        winner = ruleset.processWinningConditions(board, availableMoves, playingSides, currentSide);
        if (isFinished())
            eventEmitter.emit(new GameFinishedEvent(winner));
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

    private Side winner = null;

    private final CheckersRuleset ruleset;
    private final Board board;

    public void subscribe(EventSubscriber subscriber) {
        eventEmitter.subscribe(subscriber);
    }

    public void unsubscribe(EventSubscriber subscriber) {
        eventEmitter.subscribe(subscriber);
    }

    private final List<Side> playingSides;
    private final EventEmitter eventEmitter;
    private final MoveHistory moveHistory;

    private Side currentSide;
    private List<Move> availableMoves;

    private final AvailableMovesBuilder availableMovesBuilder;
    private final FinalizationArgumentsBuilder finalizationArgumentsBuilder;
}
