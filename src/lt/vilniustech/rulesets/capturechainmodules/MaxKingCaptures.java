package lt.vilniustech.rulesets.capturechainmodules;

import lt.vilniustech.Board;
import lt.vilniustech.moves.base.AbstractCaptureMove;
import lt.vilniustech.rulesets.capturechain.CaptureChain;
import lt.vilniustech.rulesets.capturechain.CaptureChainModule;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MaxKingCaptures implements CaptureChainModule {

    public static UUID id = UUID.fromString("e703e18b-f055-4119-bd52-ca36099234c2");

    @Override
    public UUID getId() {
        return MaxKingCaptures.id;
    }

    public MaxKingCaptures() {
        this.captures = 0;
    }

    private MaxKingCaptures(int captures) {
        this.captures = captures;
    }

    @Override
    public boolean continueExtending() {
        return true;
    }

    @Override
    public List<CaptureChain> filter(List<CaptureChain> captureChains) {
        int maxCaptures = 0;
        for(CaptureChain captureChain: captureChains) {
            MaxKingCaptures module = module(captureChain);
            if (maxCaptures < module.captures) {
                maxCaptures = module.captures;
            }
        }
        final int finalMaxCaptures = maxCaptures;

        return captureChains.stream()
                .filter(captureChain -> module(captureChain).captures == finalMaxCaptures)
                .collect(Collectors.toList());
    }

    private MaxKingCaptures module(CaptureChain captureChain) {
        return (MaxKingCaptures) captureChain.getModule(getId());
    }

    @Override
    public CaptureChainModule extend(CaptureChainModule module) {
        if(module instanceof MaxKingCaptures) {
            MaxKingCaptures other = (MaxKingCaptures)module;
            return new MaxKingCaptures(captures + other.captures);
        }
        return new MaxKingCaptures(captures);
    }

    @Override
    public CaptureChainModule initialize(Board board, AbstractCaptureMove move) {
        return new MaxKingCaptures(board.getPiece(move.getOver()).isKing() ? 1 : 0);
    }

    private final int captures;
}
