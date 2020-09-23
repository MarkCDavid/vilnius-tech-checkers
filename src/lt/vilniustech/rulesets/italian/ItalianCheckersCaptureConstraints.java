package lt.vilniustech.rulesets.italian;

import lt.vilniustech.Board;
import lt.vilniustech.Side;
import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.CaptureConstraints;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ItalianCheckersCaptureConstraints implements CaptureConstraints {

    public ItalianCheckersCaptureConstraints(Board board, Move move) {
        this.board = board;
        this.move = move;
    }

    @Override
    public List<Move> filterMoves(List<Move> availableMoves) {

        List<Move> captureMoves = availableMoves.stream()
                .filter(ItalianCheckersFilters.getCaptureMoveFilter())
                .filter(ItalianCheckersFilters.getExcludeMenCaptureKingMoveFilter(board))
                .collect(Collectors.toList());

        if(captureMoves.size() > 0) {
            List<CaptureChain> captureChains = CaptureChain.construct(board, captureMoves);
            if(captureChains.size() > 1) captureChains = getChainsWithMaxCaptures(captureChains);
            if(captureChains.size() > 1) captureChains = getCapturesWithKing(captureChains);
            if(captureChains.size() > 1) captureChains = getChainsWithMaxKingCaptures(captureChains);
            if(captureChains.size() > 1) captureChains = getChainsWithEarliestKing(captureChains);
            return captureChains.stream().map(CaptureChain::getMove).collect(Collectors.toList());
        }
        else {
            return availableMoves;
        }
    }

    @Override
    public Side getNextSide(Side side) {
        return multipleCaptures ? side : Side.opposite(side);
    }

    @Override
    public void setMultipleCaptures(boolean multipleCaptures) {
        this.multipleCaptures = multipleCaptures;
    }

    public static List<CaptureChain> getChainsWithMaxCaptures(List<CaptureChain> captureChains) {
        int maxCaptures = 0;
        for(CaptureChain captureChain: captureChains) {
            if (maxCaptures < captureChain.getCapturedPieces()) {
                maxCaptures = captureChain.getCapturedPieces();
            }
        }
        final int finalMaxCaptures = maxCaptures;

        return captureChains.stream()
                .filter(captureChain -> captureChain.getCapturedPieces() == finalMaxCaptures)
                .collect(Collectors.toList());
    }

    public static List<CaptureChain> getChainsWithMaxKingCaptures(List<CaptureChain> captureChains) {
        int maxCaptures = 0;
        for(CaptureChain captureChain: captureChains) {
            if (maxCaptures < captureChain.getCapturedKings()) {
                maxCaptures = captureChain.getCapturedKings();
            }
        }
        final int finalMaxCaptures = maxCaptures;

        return captureChains.stream()
                .filter(captureChain -> captureChain.getCapturedKings() == finalMaxCaptures)
                .collect(Collectors.toList());
    }



    public static List<CaptureChain> getChainsWithEarliestKing(List<CaptureChain> captureChains) {
        int earliestKing = Integer.MAX_VALUE;
        for(CaptureChain captureChain: captureChains) {
            if (earliestKing > captureChain.getFirstKingCapture()) {
                earliestKing = captureChain.getFirstKingCapture();
            }
        }
        final int finalEarliestKing = earliestKing;

        return captureChains.stream()
                .filter(captureChain -> captureChain.getFirstKingCapture() == finalEarliestKing)
                .collect(Collectors.toList());
    }



    public static List<CaptureChain> getCapturesWithKing(List<CaptureChain> captureChains) {
        List<CaptureChain> capturesWithKing = captureChains.stream()
                .filter(CaptureChain::captureWithKing)
                .collect(Collectors.toList());

        return capturesWithKing.size() > 0 ? capturesWithKing : captureChains;
    }

    private boolean multipleCaptures;

    private final Board board;
    private final Move move;
}
