package lt.vilniustech.manager;

import lt.vilniustech.*;
import lt.vilniustech.moves.*;
import lt.vilniustech.moves.base.CaptureMove;
import lt.vilniustech.moves.base.Move;
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
            for(int jumpSize = 1; jumpSize <= board.getBoardSize() - moveSize; jumpSize++) {

                if(jumpSize > 1 && !ruleset.canJumpAnywhereBeyond(piece))
                    return moves;

                CaptureMove capture = ruleset.isCaptureImmediate() ?
                        new ImmediateCaptureMove(piece.getCoordinate(), direction, moveSize, jumpSize) :
                        new NonImmediateCaptureMove(piece.getCoordinate(), direction, moveSize, jumpSize);

                if (capture.isValid(board)) {
                    boolean valid = true;
                    List<Move> history = moveHistorySupport.getMoveHistory();
                    for(int i = history.size() - 1; i >= 0; i--) {
                        Move previousMove = history.get(i);
                        if(!valid || !previousMove.isCapture()) {
                            break;
                        }

                        CaptureMove previousCaptureMove = (CaptureMove) previousMove;

                        Piece currentOverPiece = board.getPiece(capture.getOver());
                        Piece previousOverPiece = board.getPiece(previousCaptureMove.getOver());

                        if(currentOverPiece == previousOverPiece) {
                            valid = false;
                        }
                    }

                    if(valid)
                        moves.add(capture);
                }
            }
        }
        return moves;
    }

    private List<Move> buildSimpleMovesToDirection(Piece piece, Direction direction) {
        List<Move> moves = new ArrayList<>();
        for(int moveSize = 1; moveSize <= piece.getMoveSize(); moveSize++) {
            Move simple = new SimpleMove(piece.getCoordinate(), direction, moveSize);
            if (simple.isValid(board))
                moves.add(simple);
        }
        return moves;
    }

    public AvailableMovesBuilder(Board board, MoveHistorySupport moveHistorySupport, CheckersRuleset ruleset) {
        this.board = board;
        this.ruleset = ruleset;
        this.moveHistorySupport = moveHistorySupport;
    }

    private final Board board;
    private final CheckersRuleset ruleset;
    private final MoveHistorySupport moveHistorySupport;
}
