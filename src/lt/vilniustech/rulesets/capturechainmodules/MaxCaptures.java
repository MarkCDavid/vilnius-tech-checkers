package lt.vilniustech.rulesets.capturechainmodules;

import lt.vilniustech.Board;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.rulesets.capturechain.CaptureChain;
import lt.vilniustech.rulesets.capturechain.CaptureChainModule;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MaxCaptures implements CaptureChainModule {

    public static UUID id = UUID.fromString("ff2a4c9d-55d1-4ea6-8df4-4a5b0caef892");

    @Override
    public UUID getId() {
        return MaxCaptures.id;
    }

    public MaxCaptures() {
        this.captures = 0;
    }

    private MaxCaptures(int captures) {
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
            MaxCaptures module = module(captureChain);
            if (maxCaptures < module.captures) {
                maxCaptures = module.captures;
            }
        }
        final int finalMaxCaptures = maxCaptures;

        return captureChains.stream()
                .filter(captureChain -> module(captureChain).captures == finalMaxCaptures)
                .collect(Collectors.toList());
    }

    private MaxCaptures module(CaptureChain captureChain) {
        return (MaxCaptures) captureChain.getModule(getId());
    }

    @Override
    public CaptureChainModule extend(CaptureChainModule module) {
        if(module instanceof MaxCaptures) {
            MaxCaptures other = (MaxCaptures)module;
            return new MaxCaptures(captures + other.captures);
        }
        return new MaxCaptures(captures);
    }

    @Override
    public CaptureChainModule initialize(Board board, CaptureMove move) {
        return new MaxCaptures(1);
    }

    private final int captures;
}
