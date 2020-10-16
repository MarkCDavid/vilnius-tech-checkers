package backend.moves;

import backend.Board;
import backend.Coordinate;
import backend.Piece;
import backend.manager.MoveHistory;
import backend.moves.base.CaptureMove;
import backend.moves.base.Move;
import backend.moves.finalization.FinalizationArguments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NonImmediateFinalCaptureMove extends CaptureMove {

    public NonImmediateFinalCaptureMove(Coordinate from, Coordinate over, Coordinate to, MoveHistory history, boolean promote) {
        super(from, over, to);

        this.captureMoves = new ArrayList<>();

        for(Move previousMove : history.backwards()) {
            if(!previousMove.hasUncaptured())
                break;

            this.captureMoves.add((CaptureMove)previousMove);
        }

        this.promotionMove = promote;
        this.capturedPieces = new HashMap<>();
    }

    @Override
    public boolean hasUncaptured() {
        return false;
    }

    @Override
    public void apply(Board board) {
        super.apply(board);



        for(CaptureMove captureMove: captureMoves) {
            capturedPieces.put(captureMove, board.popPiece(captureMove.getOver()));
        }

        capturedPieces.put(this, board.popPiece(over));
        capturedPiece = capturedPieces.get(this);

        unpromotedPiece = board.popPiece(from);
        board.putPiece(to, promotionMove ? unpromotedPiece.promote() : unpromotedPiece);
    }

    @Override
    public void revert(Board board) {
        super.revert(board);

        for(CaptureMove captureMove: captureMoves) {
            board.putPiece(captureMove.getOver(), capturedPieces.get(captureMove));
        }

        board.putPiece(over, capturedPieces.get(this));

        board.popPiece(to);
        board.putPiece(from, unpromotedPiece);
    }

    @Override
    public Move finalizeMove(Board board, MoveHistory history, FinalizationArguments argumentType) {
        promotionMove = argumentType.isPromote();
        return this;
    }

    private final List<CaptureMove> captureMoves;
    private final HashMap<CaptureMove, Piece> capturedPieces;
}
