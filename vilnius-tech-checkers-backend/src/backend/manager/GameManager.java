package backend.manager;

import api.dto.Ruleset;
import backend.Board;
import backend.Coordinate;
import backend.events.EventEmitter;
import backend.events.EventSubscriber;
import backend.events.SubscriptionSupport;
import backend.factory.RulesetFactory;
import backend.manager.events.event.GameFinishedEvent;
import backend.manager.events.event.MoveProcessedEvent;
import backend.manager.events.event.SideSwitchEvent;
import backend.manager.events.handler.SideSwitchEventSubscriber;
import backend.manager.exceptions.GameFinishedException;
import backend.manager.exceptions.IllegitimateMoveException;
import backend.moves.base.Move;
import backend.moves.finalization.FinalizationArguments;
import backend.moves.finalization.FinalizationArgumentsBuilder;
import backend.rulesets.CheckersRuleset;
import backend.side.Side;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameManager implements SubscriptionSupport {

    public Board getBoard() {
        return board;
    }

    public GameManager(CheckersRuleset ruleset) {
        this.moveHistory = new MoveHistory();
        this.eventEmitter = new EventEmitter();

        this.ruleset = ruleset;
        this.sides = new GameSides(this.ruleset.getPlayingSides());
        this.board = new Board(this.ruleset.getBoardSize());

        for(var side: sides.getPlayingSides()) {
            side.fillBoard(board);
        }

        this.sides.subscribe(constructSideSwitchHandler());

        this.availableMovesBuilder = new AvailableMovesBuilder(board, moveHistory, this.ruleset.getMoveFactory());
        this.finalizationArgumentsBuilder = new FinalizationArgumentsBuilder(board, this.ruleset, moveHistory, this.availableMovesBuilder);

        this.availableMoves = availableMovesBuilder.buildAvailableMoves(sides.getCurrentSide());
    }

    public void processMove(Move move) {
        if(sides.isFinished())
            throw new GameFinishedException(sides.getWinner());

        if(!legitimateMove(move))
            throw new IllegitimateMoveException(move);

        FinalizationArguments arguments = finalizationArgumentsBuilder.build(sides.getCurrentSide(), move);
        move = move.finalizeMove(board, moveHistory, arguments);
        move.apply(board);
        moveHistory.add(move);
        eventEmitter.emit(new MoveProcessedEvent(move));

        if(arguments.isPromote())
            board.putPiece(move.getTo(), board.popPiece(move.getTo()).promote());

        if(arguments.isSwitchSide())
            sides.next();

        Side next = ruleset.processWinningConditions(board, availableMoves, sides.getPlayingSides(), sides.getCurrentSide());
        if(!Objects.equals(sides.getCurrentSide(), next)) {
            sides.next();
        }

        if (sides.isFinished())
            eventEmitter.emit(new GameFinishedEvent(sides.getWinner()));
    }

    private SideSwitchEventSubscriber constructSideSwitchHandler() {
        return new SideSwitchEventSubscriber() {
            @Override
            protected void receive(SideSwitchEvent event) {
                var captureConstraints = ruleset.getCaptureConstraints(board, moveHistory, null);
                availableMoves = captureConstraints.filterMoves(availableMovesBuilder.buildAvailableMoves(event.getCurrent()));
            }
        };
    }

    private boolean legitimateMove(Move move) {
        return availableMoves.contains(move);
    }

    public List<Move> getAvailableMoves() {
        return sides.isFinished() ? new ArrayList<>() : new ArrayList<>(availableMoves);
    }

    public List<Move> getAvailableMoves(Coordinate from) {
        List<Move> moves = new ArrayList<>();
        if(sides.isFinished()) return moves;

        for(Move move: getAvailableMoves()) {
            if(move.getFrom().equals(from))
                moves.add(move);
        }
        return moves;
    }

    private final CheckersRuleset ruleset;
    private final Board board;

    private final MoveHistory moveHistory;

    private List<Move> availableMoves;

    private final GameSides sides;

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
