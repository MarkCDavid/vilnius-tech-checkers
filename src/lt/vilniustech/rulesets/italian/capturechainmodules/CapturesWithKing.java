package lt.vilniustech.rulesets.italian.capturechainmodules;

import lt.vilniustech.Board;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.rulesets.CaptureChain;
import lt.vilniustech.rulesets.CaptureChainModule;

import java.util.List;
import java.util.stream.Collectors;

public class CapturesWithKing implements CaptureChainModule {


    public CapturesWithKing() {
        this.captureWithKing = false;
    }

    private CapturesWithKing(boolean captureWithKing) {
        this.captureWithKing = captureWithKing;
    }

    @Override
    public boolean continueExtending() {
        return true;
    }

    @Override
    public List<CaptureChain> filter(List<CaptureChain> captureChains, int moduleIndex) {
        List<CaptureChain> capturesWithKing = captureChains.stream()
                .filter(captureChain -> module(captureChain, moduleIndex).captureWithKing)
                .collect(Collectors.toList());

        return capturesWithKing.size() > 0 ? capturesWithKing : captureChains;
    }

    private CapturesWithKing module(CaptureChain captureChain, int moduleIndex) {
        return (CapturesWithKing) captureChain.getModule(moduleIndex);
    }

    @Override
    public CaptureChainModule extend(CaptureChainModule module) {
        return new CapturesWithKing(this.captureWithKing);
    }

    @Override
    public CaptureChainModule initialize(Board board, CaptureMove move) {
        return new CapturesWithKing(board.getCell(move.getFrom()).getPiece().isKing());
    }

    private final boolean captureWithKing;
}
