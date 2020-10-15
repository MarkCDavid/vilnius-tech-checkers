package lt.vilniustech.rulesets.turkish;

import lt.vilniustech.Board;
import lt.vilniustech.moves.MoveHistory;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.rulesets.Filters;
import lt.vilniustech.rulesets.capturechain.CaptureChain;
import lt.vilniustech.rulesets.capturechain.CaptureChainBuilder;
import lt.vilniustech.rulesets.capturechainmodules.*;

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
        this.multiCapture = multiCapture;
    }

    private boolean multiCapture;

    private final CaptureChainBuilder captureChainBuilder;
    private final Board board;
}
