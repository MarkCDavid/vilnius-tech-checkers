package lt.vilniustech.manager.state;

import lt.vilniustech.Board;
import lt.vilniustech.Side;
import lt.vilniustech.manager.MoveCollectionsBuilder;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.moves.Move;
import lt.vilniustech.moves.SimpleMove;

import java.util.ArrayList;
import java.util.List;

public class CaptureState extends State {

    public CaptureState(Board board, CaptureMove move) {
        captureMoves = new ArrayList<>();
        captureMoves.add(move);

        if(board.getRuleset().isCaptureImmediate()) return;
        setAllPieces(board);

    }

    @Override
    public State performMove(Board board, Move move) {
        Side side = getSide(board, move);

        board.applyMove(move);
        captureMoves.add((CaptureMove)move);

        if(!board.getRuleset().isCaptureImmediate())
            setAllPieces(board);

        if(hasCaptureMoves(board, move, side)) return this;

        if(!board.getRuleset().isCaptureImmediate())
            takeAllPieces(board);

        return new SimpleState();
    }

    @Override
    public Side getNextSide(Side currentSide) {
        return currentSide;
    }

    @Override
    public boolean isMultiCapture() {
        return true;
    }

    private void setAllPieces(Board board) {
        for(CaptureMove captureMove: captureMoves) {
            captureMove.setCapturedPiece(board);
        }
    }
    private void takeAllPieces(Board board) {
        for(CaptureMove captureMove: captureMoves) {
            captureMove.takeCapturedPiece(board);
        }
    }

    private final List<CaptureMove> captureMoves;
}
