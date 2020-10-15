package lt.vilniustech.rulesets.capturechain;

import lt.vilniustech.Board;
import lt.vilniustech.moves.base.CaptureMove;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.rulesets.capturechainmodules.ModuleFactory;

import java.util.*;

public class CaptureChain {

    public Move getMove() {
        return move;
    }

    public CaptureChainModule getModule(UUID module) { return modules.get(module); }

    public CaptureChain(Board board, CaptureMove move, Map<UUID, CaptureChainModule> modules) {
        this.move = move;
        this.modules = new HashMap<>();
        for(UUID moduleId: modules.keySet()) {
            this.modules.put(moduleId, ModuleFactory.make(moduleId).initialize(board, move));
        }
    }

    public CaptureChain extend(CaptureChain other) {
        CaptureChain extension = new CaptureChain();
        extension.move = this.move;

        boolean extend = true;


        for(UUID moduleId: modules.keySet()) {
            CaptureChainModule module = modules.get(moduleId);
            extend &= module.continueExtending();

            if(extend) module = module.extend(other.modules.get(moduleId));
            extend &= module.continueExtending();

            extension.modules.put(moduleId, module);
        }

        return extension;
    }

    private CaptureChain() {
        this.modules = new HashMap<>();
    }

    private Move move;
    private final Map<UUID, CaptureChainModule> modules;

}
