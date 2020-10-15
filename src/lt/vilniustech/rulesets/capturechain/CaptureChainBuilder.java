package lt.vilniustech.rulesets.capturechain;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.manager.AvailableMovesBuilder;
import lt.vilniustech.moves.base.AbstractCaptureMove;
import lt.vilniustech.moves.base.AbstractMove;
import lt.vilniustech.rulesets.CheckersRuleset;
import lt.vilniustech.rulesets.capturechainmodules.ModuleFactory;
import lt.vilniustech.side.Side;

import java.util.*;
import java.util.stream.Collectors;

public class CaptureChainBuilder
{
    public CaptureChainBuilder(CheckersRuleset ruleset) {
        this.modules = new HashMap<>();
        this.ruleset = ruleset;
    }

    public CaptureChainModule getModule(UUID module) {
        return modules.get(module);
    }

    public List<CaptureChain> build(Board board ,List<AbstractMove> moves) {
        List<CaptureChain> captureChains = new ArrayList<>();

        for(AbstractMove move: moves)
            captureChains.addAll(build(board, (AbstractCaptureMove)move));

        return captureChains;
    }

    public List<CaptureChain> build(Board board, AbstractCaptureMove move) {
        List<CaptureChain> captureChains = new ArrayList<>();
        CaptureChain root = new CaptureChain(board, move, modules);

        Side side = board.getPiece(move.getFrom()).getSide();

        move.apply(board);

        for(AbstractCaptureMove captureMove: getAvailableCaptureMoves(board, side, move.getTo())) {
            for (CaptureChain leaf : build(board, captureMove)) {
                captureChains.add(root.extend(leaf));
            }
        }

        if(captureChains.size() == 0)
            captureChains.add(root);

        move.revert(board);

        return captureChains;
    }

    private List<AbstractCaptureMove> getAvailableCaptureMoves(Board board, Side side, Coordinate from) {
        return new AvailableMovesBuilder(board, ruleset).buildAvailableMoves(board.getPiece(from)).stream()
                .filter(move -> move instanceof AbstractCaptureMove)
                .map(move -> (AbstractCaptureMove) move)
                .collect(Collectors.toList());
    }

    public void registerModule(UUID module) {
        if(modules.containsKey(module)) return;
        modules.put(module, ModuleFactory.make(module));
    }

    private final Map<UUID, CaptureChainModule> modules;
    private final CheckersRuleset ruleset;
}
