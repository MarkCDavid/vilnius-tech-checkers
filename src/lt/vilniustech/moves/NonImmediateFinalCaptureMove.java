package lt.vilniustech.moves;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Piece;
import lt.vilniustech.manager.MoveHistorySupport;
import lt.vilniustech.moves.base.CaptureMove;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.moves.finalization.FinalizationArguments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NonImmediateFinalCaptureMove extends CaptureMove {

    public NonImmediateFinalCaptureMove(Coordinate from, Coordinate over, Coordinate to, List<Move> captureMoves) {
        super(from, over, to);

        this.captureMoves = new ArrayList<>();

        for(int i = captureMoves.size() - 1; i >= 0; i--) {
            Move captureMove = captureMoves.get(i);
            if(!(captureMove instanceof NonImmediateCaptureMove))
                break;

            this.captureMoves.add((CaptureMove)captureMove);
        }

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

        capturedPieces.put(this, board.popPiece(this.getOver()));
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

        board.putPiece(this.getOver(), capturedPieces.get(this));
        board.putPiece(from, board.popPiece(to));
    }

    @Override
    public Move finalizeMove(Board board, MoveHistorySupport support, FinalizationArguments argumentType) {
        return this;
    }

    private final List<CaptureMove> captureMoves;
    private final HashMap<CaptureMove, Piece> capturedPieces;
}
