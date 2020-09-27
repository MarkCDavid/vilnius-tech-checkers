package lt.vilniustech.manager;

import lt.vilniustech.*;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.moves.Move;
import lt.vilniustech.moves.SimpleMove;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.Filters;
import lt.vilniustech.utils.CoordinateIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoveCollectionsBuilder {


    public static List<Move> getAllAvailableMoves(Board board, Side side) {
        return getAllAvailableMoves(board, side, null);
    }

    public static List<Move> getAllAvailableMoves(Board board, Side side, Coordinate unavailable) {

        ArrayList<Move> availableMoves = new ArrayList<>();

        for(Coordinate from: new CoordinateIterator(board.getRuleset().getBoardSize())) {
            availableMoves.addAll(getAvailableMoves(board, side, from, unavailable));
        }

        return availableMoves;
    }

    public static List<Move> getAvailableMoves(Board board, Side side, Coordinate from) {
        return getAvailableMoves(board, side, from, null);
    }

    public static List<Move> getAvailableMoves(Board board, Side side, Coordinate from, Coordinate unavailable) {
        ArrayList<Move> availableMoves = new ArrayList<>();

        Cell fromCell = board.getCell(from);
        if(fromCell == null) return availableMoves;

        Piece fromPiece = fromCell.getPiece();
        if(fromPiece == null || fromPiece.getSide() != side) return availableMoves;

        for(Direction direction : fromPiece.getDirections()) {
            for(int moveSize = 1; moveSize <= fromPiece.getMoveSize(); moveSize++) {
                Move move = getMove(board, from, direction, moveSize);
                if(move == null || (unavailable != null && move.getTo().equals(unavailable))) break;
                availableMoves.add(move);
                if(move instanceof CaptureMove) break;

            }
        }

        return availableMoves;
    }

    private static Move getMove(Board board, Coordinate from, Direction direction, int moveSize) {
        Move simple = new SimpleMove(from, direction, moveSize);
        if(simple.isValid(board))
            return simple;

        Move capture = new CaptureMove(from, direction, moveSize + 1);
        if (capture.isValid(board))
            return capture;

        return null;
    }
}
