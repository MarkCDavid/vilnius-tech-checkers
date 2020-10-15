package lt.vilniustech.manager;

import lt.vilniustech.*;
import lt.vilniustech.moves.*;
import lt.vilniustech.moves.base.CaptureMove;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.moves.factory.MoveFactory;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.side.Side;

import java.util.ArrayList;
import java.util.List;

public class AvailableMovesBuilder {

    public List<Move> buildAvailableMoves(Side side) {
        ArrayList<Move> availableMoves = new ArrayList<>();

        for(Piece piece: side.getPieces(board)) {
            availableMoves.addAll(buildAvailableMoves(piece));
        }

        return availableMoves;
    }

    public List<Move> buildAvailableMoves(Piece piece) {
        List<Move> moves = new ArrayList<>();

        if(piece == null)
            return moves;

        for(Direction direction: piece.getDirections()) {
            moves.addAll(buildMovesToDirection(piece, direction));
        }

        return moves;
    }

    private List<Move> buildMovesToDirection(Piece piece, Direction direction) {
        if(direction.isCaptureOnly())
            return buildCaptureMovesToDirection(piece, direction);

        List<Move> moves = new ArrayList<>();
        moves.addAll(buildSimpleMovesToDirection(piece, direction));
        moves.addAll(buildCaptureMovesToDirection(piece, direction));
        return moves;
    }

    private List<Move> buildCaptureMovesToDirection(Piece piece, Direction direction) {
        List<Move> moves = new ArrayList<>();
        for(int moveSize = 1; moveSize <= piece.getMoveSize(); moveSize++) {

            if(obstacles(piece, direction, moveSize))
                break;

            for(int jumpSize = 1; jumpSize <= board.getBoardSize() - moveSize; jumpSize++) {

                if(jumpSize > 1 && !ruleset.canJumpAnywhereBeyond(piece))
                    break;

                var captureMove = moveFactory.createCaptureMove(piece.getCoordinate(), direction, moveSize, jumpSize);

                if (captureMove.isValid(board) && !jumpOverJumped(captureMove)) {
                    moves.add(captureMove);
                }
            }
        }
        return moves;
    }

    private List<Move> buildSimpleMovesToDirection(Piece piece, Direction direction) {
        List<Move> moves = new ArrayList<>();
        for(int moveSize = 1; moveSize <= piece.getMoveSize(); moveSize++) {
            Move simple = moveFactory.createMove(piece.getCoordinate(), direction, moveSize);
            if (!simple.isValid(board))
                break;
            moves.add(simple);
        }
        return moves;
    }

    private boolean jumpOverJumped(CaptureMove captureMove) {
        for(Move previousMove: moveHistory.backwards()) {
            if(!previousMove.hasUncaptured())
                return false;

            var previousCaptureMove = (CaptureMove) previousMove;
            if(board.getPiece(captureMove.getOver()) == previousCaptureMove.getCapturedPiece())
                return true;
        }
        return false;
    }

    private boolean obstacles(Piece piece, Direction direction, int moveSize) {
        if(moveSize <= 1)
            return false;

        var obstacle = piece.getCoordinate().move(direction, moveSize - 1);
        return board.getPiece(obstacle) != null;
    }

    public AvailableMovesBuilder(Board board, CheckersRuleset ruleset, MoveHistory moveHistory) {
        this.board = board;
        this.ruleset = ruleset;
        this.moveHistory = moveHistory;
        this.moveFactory = ruleset.getMoveFactory();
    }

    private final Board board;
    private final CheckersRuleset ruleset;
    private final MoveHistory moveHistory;
    private final MoveFactory moveFactory;
}
