package lt.vilniustech.rulesets.italian.capturechainmodules;

import lt.vilniustech.Board;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.rulesets.CaptureChain;
import lt.vilniustech.rulesets.CaptureChainModule;

import java.util.List;
import java.util.stream.Collectors;

public class MaxKingCaptures implements CaptureChainModule {

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
    public List<CaptureChain> filter(List<CaptureChain> captureChains, int moduleIndex) {
        int maxCaptures = 0;
        for(CaptureChain captureChain: captureChains) {
            MaxKingCaptures module = module(captureChain, moduleIndex);
            if (maxCaptures < module.captures) {
                maxCaptures = module.captures;
            }
        }
        final int finalMaxCaptures = maxCaptures;

        return captureChains.stream()
                .filter(captureChain -> module(captureChain, moduleIndex).captures == finalMaxCaptures)
                .collect(Collectors.toList());
    }

    private MaxKingCaptures module(CaptureChain captureChain, int moduleIndex) {
        return (MaxKingCaptures) captureChain.getModule(moduleIndex);
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
    public CaptureChainModule initialize(Board board, CaptureMove move) {
        return new MaxKingCaptures(board.getCell(move.getOver()).getPiece().isKing() ? 1 : 0);
    }

    private final int captures;
}
