package backend.rulesets.turkish;

import backend.Board;
import backend.manager.MoveHistory;
import backend.moves.base.Move;
import backend.rulesets.CaptureConstraints;
import backend.rulesets.CheckersRuleset;
import backend.rulesets.Filters;
import backend.rulesets.capturechain.CaptureChain;
import backend.rulesets.capturechain.CaptureChainBuilder;
import backend.rulesets.capturechainmodules.*;

import java.util.List;
import java.util.stream.Collectors;

public class TurkishCheckersCaptureConstraints implements CaptureConstraints {

    public TurkishCheckersCaptureConstraints(Board board, CheckersRuleset ruleset, MoveHistory support, Move move) {
        this.board = board;

        this.captureChainBuilder = new CaptureChainBuilder(ruleset, support);
        this.captureChainBuilder.registerModule(MaxCaptures.id);
    }

    @Override
    public List<Move> filterMoves(List<Move> availableMoves) {
        List<Move> captureMoves = availableMoves.stream()
                .filter(Filters.captureMoves())
                .collect(Collectors.toList());

        if (captureMoves.isEmpty()) {
            return availableMoves;
        } else {
            List<CaptureChain> captureChains = captureChainBuilder.build(board, captureMoves);
            if(captureChains.size() > 1) captureChains = captureChainBuilder.getModule(MaxCaptures.id).filter(captureChains);
            return captureChains.stream().map(CaptureChain::getMove).collect(Collectors.toList());
        }
    }

    @Override
    public void setMultiCapture(boolean multiCapture) {

    }



    private final CaptureChainBuilder captureChainBuilder;
    private final Board board;
}
