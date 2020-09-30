package lt.vilniustech.rulesets.capturechainmodules;

import lt.vilniustech.Board;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.rulesets.capturechain.CaptureChain;
import lt.vilniustech.rulesets.capturechain.CaptureChainModule;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EarliestKingCapture implements CaptureChainModule {

    public static UUID id = UUID.fromString("7eef86b3-19e8-4ea6-9d8d-daa75124afbd");

    @Override
    public UUID getId() {
        return EarliestKingCapture.id;
    }

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
    public List<CaptureChain> filter(List<CaptureChain> captureChains) {
        int earliestKingCapture = Integer.MAX_VALUE;
        for(CaptureChain captureChain: captureChains) {
            EarliestKingCapture module = module(captureChain);
            if (earliestKingCapture > module.earliestKingCapture) {
                earliestKingCapture = module.earliestKingCapture;
            }
        }
        final int finalEarliestKingCapture = earliestKingCapture;

        return captureChains.stream()
                .filter(captureChain -> module(captureChain).earliestKingCapture == finalEarliestKingCapture)
                .collect(Collectors.toList());
    }

    private EarliestKingCapture module(CaptureChain captureChain) {
        return (EarliestKingCapture) captureChain.getModule(getId());
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
        return new EarliestKingCapture(board.getPiece(move.getOver()).isKing());
    }

    private final boolean kingCapture;
    private final int earliestKingCapture;
}
