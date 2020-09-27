package lt.vilniustech.manager;

import lt.vilniustech.*;
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

    public GameManager(CheckersRuleset ruleset) {
        this.ruleset = ruleset;
        this.board = new Board(ruleset);
        this.currentSide = ruleset.getFirstToMove();
        this.availableMoves =  getAvailableMoves(currentSide);
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

    private boolean applyMove(Move move) {
        Piece piece = getBoard().getCell(move.getFrom()).getPiece();
        Side side = piece.getSide();
        boolean destinationIsKingRow = ruleset.isKingRow(side, move.getTo());


        move.apply(getBoard());
        if(destinationIsKingRow && !piece.isKing()) {
            Piece kingPiece = ruleset.createKing(side);
            getBoard().getCell(move.getTo()).setPiece(kingPiece);
            return false;
        }
        else {
            return getAvailableMoves(side, move.getTo()).stream().anyMatch(m -> m instanceof CaptureMove);
        }
    }

    public List<Move> getAvailableMoves(Side side) {

        ArrayList<Move> availableMoves = new ArrayList<>();

        for(Coordinate from: new CoordinateIterator(ruleset.getBoardSize())) {
            availableMoves.addAll(getAvailableMoves(side, from));
        }

        return availableMoves;
    }


    public List<Move> getAvailableMoves(Side side, Coordinate from) {
        ArrayList<Move> availableMoves = new ArrayList<>();

        Cell fromCell = getBoard().getCell(from);
        if(fromCell == null) return availableMoves;

        Piece fromPiece = fromCell.getPiece();
        if(fromPiece == null || fromPiece.getSide() != side) return availableMoves;

        for(Direction direction : fromPiece.getDirections()) {
            for(int moveSize = 1; moveSize <= fromPiece.getMoveSize(); moveSize++) {
                Move move = getMove(from, direction, moveSize);
                if(move == null) break;
                availableMoves.add(move);
                if(move instanceof CaptureMove) break;

            }
        }

        return availableMoves;
    }

    public boolean performMove(Move move) {
        if(isFinished()) throw new GameFinishedException(getWinner());

        CaptureConstraints captureConstraints = ruleset.getCaptureConstraints(this, move);
        boolean capturesAvailable = applyMove(move);
        captureConstraints.setMultipleCaptures(capturesAvailable);
        currentSide = captureConstraints.getNextSide(currentSide);
        availableMoves = captureConstraints.filterMoves(getAvailableMoves(currentSide));

        winner = ruleset.processWinningConditions(
                availableMoves,
                board.getSidePieces(Side.WHITE),
                board.getSidePieces(Side.BLACK)
        );
        return isFinished();
    }

    private Move getMove(Coordinate from, Direction direction, int moveSize) {
        Move simple = new SimpleMove(from, direction, moveSize);
        if(simple.isValid(getBoard()))
            return simple;

        Move capture = new CaptureMove(from, direction, moveSize + 1);
        if(capture.isValid(getBoard()))
            return capture;

        return null;
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

    private List<Move> availableMoves;

    private Side winner = Side.NONE;
    private Side currentSide;

    private final CheckersRuleset ruleset;
    private final Board board;
}
