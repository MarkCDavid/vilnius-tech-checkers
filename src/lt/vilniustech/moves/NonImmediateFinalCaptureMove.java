package lt.vilniustech.moves;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Piece;

import java.util.HashMap;
import java.util.List;

public class NonImmediateFinalCaptureMove extends CaptureMove {

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

    private List<CaptureMove> captureMoves;
    private HashMap<CaptureMove, Piece> capturedPieces;
}
