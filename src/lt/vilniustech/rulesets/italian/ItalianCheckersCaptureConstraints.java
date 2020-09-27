package lt.vilniustech.rulesets.italian;

import lt.vilniustech.Board;
import lt.vilniustech.Side;
import lt.vilniustech.manager.GameManager;
import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.capturechain.CaptureChain;
import lt.vilniustech.rulesets.capturechain.CaptureChainBuilder;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.Filters;
import lt.vilniustech.rulesets.capturechainmodules.*;

import java.util.List;
import java.util.stream.Collectors;

public class ItalianCheckersCaptureConstraints implements CaptureConstraints {

    public ItalianCheckersCaptureConstraints(GameManager manager, Move move) {
        this.manager = manager;

        this.captureChainBuilder = new CaptureChainBuilder();
        this.captureChainBuilder.registerModule(ManCannotCaptureKing.id);
        this.captureChainBuilder.registerModule(MaxCaptures.id);
        this.captureChainBuilder.registerModule(CapturesWithKing.id);
        this.captureChainBuilder.registerModule(MaxKingCaptures.id);
        this.captureChainBuilder.registerModule(EarliestKingCapture.id);
    }

    @Override
    public List<Move> filterMoves(List<Move> availableMoves) {
        List<Move> captureMoves = availableMoves.stream()
                .filter(Filters.captureMoves())
                .collect(Collectors.toList());

        if(captureMoves.size() > 0) {
            List<CaptureChain> captureChains = captureChainBuilder.build(manager, captureMoves);
            if(captureChains.size() > 1) captureChains = captureChainBuilder.getModule(MaxCaptures.id).filter(captureChains);
            if(captureChains.size() > 1) captureChains = captureChainBuilder.getModule(CapturesWithKing.id).filter(captureChains);
            if(captureChains.size() > 1) captureChains = captureChainBuilder.getModule(MaxKingCaptures.id).filter(captureChains);
            if(captureChains.size() > 1) captureChains = captureChainBuilder.getModule(EarliestKingCapture.id).filter(captureChains);
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

    private boolean multipleCaptures;

    private final CaptureChainBuilder captureChainBuilder;
    private final GameManager manager;
}
