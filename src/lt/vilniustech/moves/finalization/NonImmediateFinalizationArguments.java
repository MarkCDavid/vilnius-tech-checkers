package lt.vilniustech.moves.finalization;

import lt.vilniustech.moves.CaptureMove;

import java.util.List;

public class NonImmediateFinalizationArguments implements FinalizationArguments {

    public List<CaptureMove> getCaptureMoves() {
        return captureMoves;
    }

    private final List<CaptureMove> captureMoves;

    public NonImmediateFinalizationArguments(List<CaptureMove> captureMoves) {
        this.captureMoves = captureMoves;
    }
}
