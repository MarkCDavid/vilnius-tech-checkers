package lt.vilniustech.rulesets;

import lt.vilniustech.Board;
import lt.vilniustech.Cell;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Side;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.italian.ItalianCheckersFilters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class CaptureChain {

    public Move getMove() {
        return move;
    }

    public CaptureChainModule getModule(int index) { return modules.get(index); }

    public CaptureChain(Board board, CaptureMove move, List<CaptureChainModule> modules) {
        this.move = move;
        this.modules = modules.stream().map(module -> module.initialize(board, move)).collect(Collectors.toList());
    }

    public CaptureChain extend(CaptureChain other) {
        CaptureChain extension = new CaptureChain();
        extension.move = this.move;

        boolean extend = true;
        for(int i = 0; i < modules.size(); i++) {
            CaptureChainModule module = modules.get(i);
            extend &= module.continueExtending();

            if(extend) module = module.extend(other.modules.get(i));
            extend &= module.continueExtending();

            extension.modules.add(module);
        }

        return extension;
    }

    private CaptureChain() {
        this.modules = new ArrayList<>();
    }

    private Move move;
    private final List<CaptureChainModule> modules;

}
