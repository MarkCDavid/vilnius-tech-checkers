package lt.vilniustech.moves;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Piece;
import lt.vilniustech.moves.base.AbstractCaptureMove;
import lt.vilniustech.moves.base.CaptureMove;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.moves.finalization.EmptyFinalizationArguments;
import lt.vilniustech.moves.finalization.Finajv;

import java.util.HashMap;
import java.util.List;

public class NonImmediateFinalCaptureMove extends AbstractCaptureMove<NonImmediateFinalCaptureMove, EmptyFinalizationArguments> {

    public NonImmediateFinalCaptureMove(Coordinate from, Coordinate over, Coordinate to, List<CaptureMove> captureMoves) {
        super(from, over, to);
        this.captureMoves = captureMoves;
        this.capturedPieces = new HashMap<>();
    }

    @Override
    public void apply(Board board) {
        if(isApplied()) return;
        Piece overPiece = board.getPiece(this.over);

        if(overPiece == null) return;

        applied = true;

        for(CaptureMove captureMove: captureMoves) {
            capturedPieces.put(captureMove, board.popPiece(captureMove.getOver()));
        }

        if(board.getPiece(from) == null)
            return;

        board.putPiece(to, board.popPiece(from));
    }

    @Override
    public void revert(Board board) {
        if(!isApplied()) return;

        Piece overPiece = board.getPiece(this.over);
        if(overPiece != null) return;

        applied = false;

        for(CaptureMove captureMove: captureMoves) {
            board.putPiece(captureMove.getOver(), capturedPieces.get(captureMove));
        }

        board.putPiece(from, board.popPiece(to));
    }

    @Override
    public NonImmediateFinalCaptureMove finalize(Board board, EmptyFinalizationArguments argumentType) {
        return this;
    }

    @Override
    public NonImmediateFinalCaptureMove finalizeMove(Board board) {
        return finalize(board, new EmptyFinalizationArguments());
    }

    @Override
    public Move finalizeMove(Board board, Finajv finajv) {
        return this;
    }

    private final List<CaptureMove> captureMoves;
    private final HashMap<CaptureMove, Piece> capturedPieces;
}
