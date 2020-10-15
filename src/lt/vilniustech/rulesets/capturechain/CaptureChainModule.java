package lt.vilniustech.rulesets.capturechain;

import lt.vilniustech.Board;
import lt.vilniustech.moves.base.AbstractCaptureMove;

import java.util.List;
import java.util.UUID;

public interface CaptureChainModule {

    UUID getId();
    boolean continueExtending();
    List<CaptureChain> filter(List<CaptureChain> captureChains);
    CaptureChainModule extend(CaptureChainModule module);
    CaptureChainModule initialize(Board board, AbstractCaptureMove move);
}
