package lt.vilniustech.rulesets.italian.capturechainmodules;

import lt.vilniustech.Board;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.rulesets.CaptureChain;
import lt.vilniustech.rulesets.CaptureChainModule;

import java.util.List;
import java.util.stream.Collectors;

public class EarliestKingCapture implements CaptureChainModule {

    public EarliestKingCapture() {
        this.kingCapture = false;
        this.earliestKingCapture = 0;
    }

    private EarliestKingCapture(boolean kingCapture) {
        this.kingCapture = kingCapture;
        this.earliestKingCapture = kingCapture ? 0 : Integer.MAX_VALUE;
    }

    private EarliestKingCapture(boolean kingCapture, int earliestKingCapture) {
        this.kingCapture = kingCapture;
        this.earliestKingCapture = earliestKingCapture;
    }

    @Override
    public boolean continueExtending() {
        return true;
    }

    @Override
    public List<CaptureChain> filter(List<CaptureChain> captureChains, int moduleIndex) {
        int earliestKingCapture = Integer.MAX_VALUE;
        for(CaptureChain captureChain: captureChains) {
            EarliestKingCapture module = module(captureChain, moduleIndex);
            if (earliestKingCapture > module.earliestKingCapture) {
                earliestKingCapture = module.earliestKingCapture;
            }
        }
        final int finalEarliestKingCapture = earliestKingCapture;

        return captureChains.stream()
                .filter(captureChain -> module(captureChain, moduleIndex).earliestKingCapture == finalEarliestKingCapture)
                .collect(Collectors.toList());
    }

    private EarliestKingCapture module(CaptureChain captureChain, int moduleIndex) {
        return (EarliestKingCapture) captureChain.getModule(moduleIndex);
    }

    @Override
    public CaptureChainModule extend(CaptureChainModule module) {
        if(module instanceof EarliestKingCapture) {
            EarliestKingCapture other = (EarliestKingCapture)module;
            return new EarliestKingCapture(kingCapture, kingCapture ? 0 : nextKingCaptureIndex(other));
        }
        return new EarliestKingCapture(kingCapture, earliestKingCapture);
    }

    private int nextKingCaptureIndex(EarliestKingCapture other) {
        if(other.earliestKingCapture == Integer.MAX_VALUE) return Integer.MAX_VALUE;
        return other.earliestKingCapture + 1;
    }

    @Override
    public CaptureChainModule initialize(Board board, CaptureMove move) {
        return new EarliestKingCapture(board.getCell(move.getOver()).getPiece().isKing());
    }

    private final boolean kingCapture;
    private final int earliestKingCapture;
}
