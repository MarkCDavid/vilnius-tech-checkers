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

    public static List<Move> getAllAvailableMoves(Board board, Side side, List<CaptureMove> unavailableMoves) {

        ArrayList<Move> availableMoves = new ArrayList<>();

        for(Coordinate from: new CoordinateIterator(board.getRuleset().getBoardSize())) {
            availableMoves.addAll(getAvailableMoves(board, side, from, unavailableMoves));
        }

        return availableMoves;
    }

    public static List<Move> getAvailableMoves(Board board, Side side, Coordinate from) {
        return getAvailableMoves(board, side, from, null);
    }

    public static List<Move> getAvailableMoves(Board board, Side side, Coordinate from, List<CaptureMove> unavailableMoves) {
        ArrayList<Move> availableMoves = new ArrayList<>();

        Cell fromCell = board.getCell(from);
        if(fromCell == null) return availableMoves;

        Piece fromPiece = fromCell.getPiece();
        if(fromPiece == null || fromPiece.getSide() != side) return availableMoves;

        for(Direction direction : fromPiece.getDirections()) {
            for(int moveSize = 1; moveSize <= fromPiece.getMoveSize(); moveSize++) {
                List<Move> moves = getMove(board, from, direction, moveSize, board.getRuleset().canJumpAnywhereBeyond(fromPiece));
                boolean hadCapture = false;
                for(Move move: moves) {
                    if(unavailableMoves != null && unavailableMoves.stream().anyMatch(m -> move instanceof CaptureMove && ((CaptureMove) move).getOver().equals(m.getOver()))) continue;
                    availableMoves.add(move);

                    if(move instanceof CaptureMove) hadCapture = true;
                }
                if(hadCapture) break;
            }
        }

        return availableMoves;
    }

    private static List<Move> getMove(Board board, Coordinate from, Direction direction, int moveSize, boolean canStopAnywhereBeyond) {
        List<Move> moves = new ArrayList<>();

        if(!direction.isCaptureOnly()) {
            Move simple = new SimpleMove(from, direction, moveSize);
            if (simple.isValid(board))
                moves.add(simple);
        }

        if(moves.size() > 0) return moves;

        for(int i = 1; i < board.getRuleset().getBoardSize(); i++) {
            Move capture = new CaptureMove(from, from.move(direction, moveSize), from.move(direction, moveSize + i));
            if (capture.isValid(board))
                moves.add(capture);

            if(!canStopAnywhereBeyond)
                return moves;
        }

        return moves;
    }
}
