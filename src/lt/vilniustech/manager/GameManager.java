package lt.vilniustech.manager;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Side;
import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.CheckersRuleset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

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

    public GameManager(CheckersRuleset ruleset) {
        this.ruleset = ruleset;
        this.board = new Board(ruleset);
        this.currentSide = ruleset.getFirstToMove();
        this.availableMoves =  board.getAvailableMoves(currentSide);
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

    public void performMove(Move move) {
        CaptureConstraints captureConstraints = ruleset.getCaptureConstraints(board, move);
        boolean capturesAvailable = board.applyMove(move);
        captureConstraints.setMultipleCaptures(capturesAvailable);
        currentSide = captureConstraints.getNextSide(currentSide);
        availableMoves = captureConstraints.filterMoves(board.getAvailableMoves(currentSide));

        winner = ruleset.processWinningConditions(
                availableMoves,
                board.getSidePieces(Side.WHITE),
                board.getSidePieces(Side.BLACK)
        );
    }

    public List<Move> getAvailableMoves() {
      return availableMoves;
    }

    public List<Move> getAvailableMoves(Coordinate from) {
        List<Move> moves = new ArrayList<>();
        for(Move move: availableMoves) {
            if(move.getFrom().equals(from))
                moves.add(move);
        }
        return moves;
    }


    private Move getMove(Coordinate from, Coordinate to, OnManagerException onException) {
        Optional<Move> move = availableMoves.stream()
                .filter(getMoveMatcher(from, to))
                .findFirst();

        if(move.isEmpty()) {
            onException.apply(new MoveUnavailableException(from, to));
            return null;
        } else {
            return move.get();
        }
    }

    private Predicate<Move> getMoveMatcher(Coordinate from, Coordinate to) {
        return move -> move.getFrom().equals(from) && move.getTo().equals(to);
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

    private List<Move> availableMoves;

    private Side winner = Side.NONE;
    private Side currentSide;

    private final CheckersRuleset ruleset;
    private final Board board;
}
