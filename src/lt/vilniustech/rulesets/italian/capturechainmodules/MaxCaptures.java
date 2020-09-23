package lt.vilniustech.rulesets.italian.capturechainmodules;

import lt.vilniustech.Board;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.CaptureChain;
import lt.vilniustech.rulesets.CaptureChainModule;

import java.util.List;
import java.util.stream.Collectors;

public class MaxCaptures implements CaptureChainModule {

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
    public List<CaptureChain> filter(List<CaptureChain> captureChains, int moduleIndex) {
        int maxCaptures = 0;
        for(CaptureChain captureChain: captureChains) {
            MaxCaptures module = module(captureChain, moduleIndex);
            if (maxCaptures < module.captures) {
                maxCaptures = module.captures;
            }
        }
        final int finalMaxCaptures = maxCaptures;

        return captureChains.stream()
                .filter(captureChain -> module(captureChain, moduleIndex).captures == finalMaxCaptures)
                .collect(Collectors.toList());
    }

    private MaxCaptures module(CaptureChain captureChain, int moduleIndex) {
        return (MaxCaptures) captureChain.getModule(moduleIndex);
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
