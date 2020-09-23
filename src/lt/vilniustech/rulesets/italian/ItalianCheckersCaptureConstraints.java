package lt.vilniustech.rulesets.italian;

import lt.vilniustech.Board;
import lt.vilniustech.Side;
import lt.vilniustech.moves.Move;
import lt.vilniustech.moves.SimpleMove;
import lt.vilniustech.rulesets.CaptureChain;
import lt.vilniustech.rulesets.CaptureChainBuilder;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.italian.capturechainmodules.*;

import java.util.List;
import java.util.stream.Collectors;

public class ItalianCheckersCaptureConstraints implements CaptureConstraints {

    public ItalianCheckersCaptureConstraints(Board board, Move move) {
        this.board = board;
        this.move = move;
        this.captureChainBuilder = new CaptureChainBuilder();

        this.captureChainBuilder.registerModule(new ManCannotCaptureKing());
        this.captureChainBuilder.registerModule(new MaxCaptures());
        this.captureChainBuilder.registerModule(new CapturesWithKing());
        this.captureChainBuilder.registerModule(new MaxKingCaptures());
        this.captureChainBuilder.registerModule(new EarliestKingCapture());
    }

    @Override
    public List<Move> filterMoves(List<Move> availableMoves) {
        List<Move> captureMoves = availableMoves.stream()
                .filter(ItalianCheckersFilters.getCaptureMoveFilter())
                .collect(Collectors.toList());

        if(captureMoves.size() > 0) {
            List<CaptureChain> captureChains = captureChainBuilder.build(board, captureMoves);

            for(int i = 0; i < captureChainBuilder.registeredModuleCount() && captureChains.size() > 1; i++) {
                captureChains = captureChainBuilder.getModule(i).filter(captureChains, i);
                if(captureChains.size() == 0)
                    return availableMoves.stream().filter(move -> move instanceof SimpleMove).collect(Collectors.toList());
            }

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
    private final Board board;
    private final Move move;
}
