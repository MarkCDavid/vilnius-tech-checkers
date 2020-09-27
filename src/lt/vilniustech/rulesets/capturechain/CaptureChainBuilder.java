package lt.vilniustech.rulesets.capturechain;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Side;
import lt.vilniustech.manager.GameManager;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.capturechainmodules.ModuleFactory;

import java.util.*;
import java.util.stream.Collectors;

public class CaptureChainBuilder
{
    public CaptureChainBuilder() {
        this.modules = new HashMap<>();
    }

    public CaptureChainModule getModule(UUID module) {
        return modules.get(module);
    }

    public List<CaptureChain> build(GameManager manager, List<Move> moves) {
        List<CaptureChain> captureChains = new ArrayList<>();

        for(Move move: moves)
            captureChains.addAll(build(manager, (CaptureMove)move));

        return captureChains;
    }

    public List<CaptureChain> build(GameManager manager, CaptureMove move) {
        List<CaptureChain> captureChains = new ArrayList<>();
        CaptureChain root = new CaptureChain(manager.getBoard(), move, modules);

        Side side = manager.getBoard().getCell(move.getFrom()).getPiece().getSide();

        move.apply(manager.getBoard());

        for(CaptureMove captureMove: getAvailableCaptureMoves(manager, side, move.getTo())) {
            for (CaptureChain leaf : build(manager, captureMove)) {
                captureChains.add(root.extend(leaf));
            }
        }

        if(captureChains.size() == 0)
            captureChains.add(root);

        move.revert(manager.getBoard());

        return captureChains;
    }

    private static List<CaptureMove> getAvailableCaptureMoves(GameManager manager, Side side, Coordinate from) {
        return manager.getAvailableMoves(side, from).stream()
                .filter(move -> move instanceof CaptureMove)
                .map(move -> (CaptureMove) move)
                .collect(Collectors.toList());
    }

    public void registerModule(UUID module) {
        if(modules.containsKey(module)) return;
        modules.put(module, ModuleFactory.make(module));
    }

    private final Map<UUID, CaptureChainModule> modules;
}
