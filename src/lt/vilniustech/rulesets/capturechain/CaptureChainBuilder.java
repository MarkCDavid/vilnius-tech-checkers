package lt.vilniustech.rulesets.capturechain;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.manager.AvailableMovesBuilder;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.moves.Move;
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

    public List<CaptureChain> build(Board board ,List<Move> moves) {
        List<CaptureChain> captureChains = new ArrayList<>();

        for(Move move: moves)
            captureChains.addAll(build(board, (CaptureMove)move));

        return captureChains;
    }

    public List<CaptureChain> build(Board board, CaptureMove move) {
        List<CaptureChain> captureChains = new ArrayList<>();
        CaptureChain root = new CaptureChain(board, move, modules);

        Side side = board.getPiece(move.getFrom()).getSide();

        move.apply(board);

        for(CaptureMove captureMove: getAvailableCaptureMoves(board, side, move.getTo())) {
            for (CaptureChain leaf : build(board, captureMove)) {
                captureChains.add(root.extend(leaf));
            }
        }

        if(captureChains.size() == 0)
            captureChains.add(root);

        move.revert(board);

        return captureChains;
    }

    private List<CaptureMove> getAvailableCaptureMoves(Board board, Side side, Coordinate from) {
        return new AvailableMovesBuilder(board, ruleset).buildAvailableMoves(board.getPiece(from)).stream()
                .filter(move -> move instanceof CaptureMove)
                .map(move -> (CaptureMove) move)
                .collect(Collectors.toList());
    }

    public void registerModule(UUID module) {
        if(modules.containsKey(module)) return;
        modules.put(module, ModuleFactory.make(module));
    }

    private final Map<UUID, CaptureChainModule> modules;
    private final CheckersRuleset ruleset;
}
