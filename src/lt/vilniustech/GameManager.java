package lt.vilniustech;

import lt.vilniustech.moves.Move;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class GameManager {

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

    public GameManager(Board board) {
        this.board = board;
        this.availableMoves = board.getAvailableMoves(currentSide);
        this.winner = null;
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

        boolean anotherJumpAvailable = board.applyMove(move);

        if(anotherJumpAvailable) {
            availableMoves = board.getAvailableMoves(currentSide, to);
        } else {
            currentSide = Side.opposite(currentSide);
            availableMoves = board.getAvailableMoves(currentSide);
        }

        if(board.getSidePieces(Side.BLACK).size() == 0) winner = Side.WHITE;
        else if(board.getSidePieces(Side.WHITE).size() == 0) winner = Side.BLACK;
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

    private Side winner;
    private Side currentSide = Side.BLACK;

    private final Board board;
}
