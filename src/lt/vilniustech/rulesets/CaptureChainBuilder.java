package lt.vilniustech.rulesets;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Side;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.moves.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CaptureChainBuilder
{
    public CaptureChainBuilder() {
        this.modules = new ArrayList<>();
    }

    public int registeredModuleCount() {
        return modules.size();
    }

    public CaptureChainModule getModule(int index) {
        return modules.get(index);
    }

    public List<CaptureChain> build(Board board, List<Move> moves) {
        List<CaptureChain> captureChains = new ArrayList<>();

        for(Move move: moves)
            captureChains.addAll(build(board, (CaptureMove)move));

        return captureChains;
    }

    public List<CaptureChain> build(Board board, CaptureMove move) {
        List<CaptureChain> captureChains = new ArrayList<>();
        CaptureChain root = new CaptureChain(board, move, modules);

        Side side = board.getCell(move.getFrom()).getPiece().getSide();

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

    private static List<CaptureMove> getAvailableCaptureMoves(Board board, Side side, Coordinate from) {
        return board.getAvailableMoves(side, from).stream()
                .filter(move -> move instanceof CaptureMove)
                .map(move -> (CaptureMove) move)
                .collect(Collectors.toList());
    }

    public void registerModule(CaptureChainModule module) {
        modules.add(module);
    }

    private final List<CaptureChainModule> modules;
}
