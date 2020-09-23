package lt.vilniustech.rulesets;

import lt.vilniustech.Board;
import lt.vilniustech.moves.CaptureMove;

import java.util.List;

public interface CaptureChainModule {

    boolean continueExtending();
    List<CaptureChain> filter(List<CaptureChain> captureChains, int moduleIndex);
    CaptureChainModule extend(CaptureChainModule module);
    CaptureChainModule initialize(Board board, CaptureMove move);
}
