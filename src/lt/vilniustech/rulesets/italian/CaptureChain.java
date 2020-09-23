package lt.vilniustech.rulesets.italian;

import lt.vilniustech.*;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.moves.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CaptureChain {

    public Move getMove() {
        return move;
    }

    public int getCapturedPieces() {
        return capturedPieces;
    }

    public int getCapturedKings() {
        return capturedKings;
    }

    public int getFirstKingCapture() {
        return firstKingCapture;
    }

    public boolean captureWithKing() {
        return withKing;
    }

    public static List<CaptureChain> construct(Board board, List<Move> moves) {
        List<CaptureChain> captureChains = new ArrayList<>();

        for(Move move: moves)
            captureChains.addAll(construct(board, move));

        return captureChains;
    }

    public static List<CaptureChain> construct(Board board, Move move) {

        List<CaptureChain> captureChains = new ArrayList<>();
        CaptureChain root = new CaptureChain(board, (CaptureMove)move);

        Side side = board.getCell(move.getFrom()).getPiece().getSide();

        move.apply(board);

        for(Move nextMove: getAvailableCaptureMoves(board, side, move.getTo())) {
            for (CaptureChain innerCaptureChain : CaptureChain.construct(board, nextMove)) {
                captureChains.add(root.extend(innerCaptureChain));
            }
        }

        if(captureChains.size() == 0)
            captureChains.add(root);

        move.revert(board);

        return captureChains;
    }

    private CaptureChain extend(CaptureChain other) {
        CaptureChain extension = new CaptureChain();
        extension.move = this.move;
        extension.capturedPieces = this.capturedPieces + other.capturedPieces;
        extension.capturedKings =  this.capturedKings + other.capturedKings;
        extension.firstKingCapture = this.capturedKing ? 0 : nextKingCaptureIndex(other);
        extension.capturedKing = this.capturedKing;
        extension.withKing = this.withKing;
        return extension;
    }

    private int nextKingCaptureIndex(CaptureChain other) {
        if(other.firstKingCapture == Integer.MAX_VALUE) return Integer.MAX_VALUE;
        return other.firstKingCapture + 1;
    }

    private CaptureChain(Board board, CaptureMove move) {
        this.move = move;
        this.capturedPieces = 1;
        this.withKing = board.getCell(move.getFrom()).getPiece().isKing();
        this.capturedKing = board.getCell(move.getOver()).getPiece().isKing();
        this.capturedKings = this.capturedKing ? 1 : 0;
        this.firstKingCapture = this.capturedKing ? 0 : Integer.MAX_VALUE;
    }

    private CaptureChain() { }

    private static List<Move> getAvailableCaptureMoves(Board board, Side side, Coordinate from) {
        return board.getAvailableMoves(side, from).stream()
                .filter(ItalianCheckersFilters.getCaptureMoveFilter())
                .filter(ItalianCheckersFilters.getExcludeMenCaptureKingMoveFilter(board))
                .collect(Collectors.toList());
    }

    private Move move;
    private int capturedPieces;
    private int capturedKings;
    private int firstKingCapture;
    private boolean capturedKing;
    private boolean withKing;


}
