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

public class AvailableMovesBuilder {

    public List<Move> buildAvailableMoves() {

        ArrayList<Move> availableMoves = new ArrayList<>();

        for(Coordinate from: new CoordinateIterator(ruleset.getBoardSize())) {
            availableMoves.addAll(buildAvailableMoves(from));
        }

        return availableMoves;
    }

    public List<Move> buildAvailableMoves(Coordinate from) {
        List<Move> moves = new ArrayList<>();

        Piece piece = board.getPiece(from);
        if(piece == null)
            return moves;

        for(Direction direction: piece.getDirections()) {
            moves.addAll(buildMovesToDirection(piece, from, direction));
        }

        return moves;
    }

    private List<Move> buildMovesToDirection(Piece piece, Coordinate from, Direction direction) {

        if(direction.isCaptureOnly())
            return buildCaptureMovesToDirection(piece, from, direction);

        List<Move> moves = new ArrayList<>();
        moves.addAll(buildSimpleMovesToDirection(piece, from, direction));
        moves.addAll(buildCaptureMovesToDirection(piece, from, direction));

        return moves;
    }

    private List<Move> buildCaptureMovesToDirection(Piece piece, Coordinate from, Direction direction) {
        List<Move> moves = new ArrayList<>();
        for(int moveSize = 1; moveSize <= piece.getMoveSize(); moveSize++) {
            for(int jumpSize = 1; jumpSize <= ruleset.getBoardSize() - moveSize; jumpSize++) {
                Move capture = new CaptureMove(from, direction, moveSize, jumpSize);
                if (capture.isValid(board))
                    moves.add(capture);
            }
        }
        return moves;
    }

    private List<Move> buildSimpleMovesToDirection(Piece piece, Coordinate from, Direction direction) {
        List<Move> moves = new ArrayList<>();
        for(int moveSize = 1; moveSize <= piece.getMoveSize(); moveSize++) {
            Move simple = new SimpleMove(from, direction, moveSize);
            if (simple.isValid(board))
                moves.add(simple);
        }
        return moves;
    }

    public AvailableMovesBuilder(Board board) {
        this.board = board;
        this.ruleset = board.getRuleset();
    }

    private final CheckersRuleset ruleset;
    private final Board board;
}
