package lt.vilniustech.manager;

import lt.vilniustech.*;
import lt.vilniustech.manager.state.SimpleState;
import lt.vilniustech.manager.state.StateMachine;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.moves.Move;
import lt.vilniustech.moves.SimpleMove;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.rulesets.Filters;
import lt.vilniustech.utils.CoordinateIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameManager {

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
        this.stateMachine = new StateMachine(new SimpleState());
        this.currentSide = ruleset.getFirstToMove();
        this.availableMoves = MoveCollectionsBuilder.getAllAvailableMoves(board, currentSide);
    }

    public void processInput(String fromString, String toString, OnManagerException onException) {
        if(isFinished()) {
            if(onException != null)
                onException.apply(new GameFinishedException(getWinner()));
            return;
        }

        Coordinate from = ofString(fromString, onException);
        if(from == null) return;

        Coordinate to = ofString(toString, onException);
        if(to == null) return;

        Move move = getMove(from, to, onException);
        if(move == null) return;

        performMove(move);
    }


    public boolean performMove(Move move) {
        if(isFinished()) throw new GameFinishedException(getWinner());

        stateMachine.performMove(board, move);

        CaptureConstraints captureConstraints = ruleset.getCaptureConstraints(board, move);
        captureConstraints.setMultipleCaptures(stateMachine.isMultiCapture());

        currentSide = stateMachine.getNextSide(currentSide);
        availableMoves = captureConstraints.filterMoves(MoveCollectionsBuilder.getAllAvailableMoves(board, currentSide, stateMachine.isMultiCapture() ? move.getFrom() : null));

        winner = ruleset.processWinningConditions(
                availableMoves,
                board.getSidePieces(Side.WHITE),
                board.getSidePieces(Side.BLACK)
        );
        return isFinished();
    }

    public List<Move> getAvailableMoves() {
        return isFinished() ? new ArrayList<>() : availableMoves;
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


    private Move getMove(Coordinate from, Coordinate to, OnManagerException onException) {
        Optional<Move> move = availableMoves.stream()
                .filter(Filters.moveFromTo(from, to))
                .findFirst();

        if(move.isEmpty()) {
            onException.apply(new MoveUnavailableException(from, to));
            return null;
        } else {
            return move.get();
        }
    }



    private Coordinate ofString(String coordinate, OnManagerException onException) {
        try {
            return Coordinate.ofString(coordinate);
        }
        catch (IllegalCoordinateException exception) {
            if(onException != null)
                onException.apply(exception);
            return null;
        }
    }

    private final StateMachine stateMachine;

    private List<Move> availableMoves;
//    private List<Move> captureChain;

    private Side winner = Side.NONE;
    private Side currentSide;

    private final CheckersRuleset ruleset;
    private final Board board;
}
