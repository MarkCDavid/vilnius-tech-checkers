package backend.rulesets.capturechainmodules;

import backend.rulesets.capturechain.CaptureChainModule;

import java.util.UUID;

public class ModuleFactory {

    public static CaptureChainModule make(UUID module) {
        if (CapturesWithKing.id.equals(module))
            return new CapturesWithKing();
        else if (EarliestKingCapture.id.equals(module))
            return new EarliestKingCapture();
        else if (ManCannotCaptureKing.id.equals(module))
            return new ManCannotCaptureKing();
        else if (MaxCaptures.id.equals(module))
            return new MaxCaptures();
        else if (MaxKingCaptures.id.equals(module))
            return new MaxKingCaptures();

        String message = String.format("Module with id %s does not exist in the factory!", module.toString());
        throw new IllegalArgumentException(message);
    }
}
