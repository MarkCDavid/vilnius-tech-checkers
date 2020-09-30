package lt.vilniustech.rulesets.capturechainmodules;

import lt.vilniustech.Board;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.rulesets.capturechain.CaptureChain;
import lt.vilniustech.rulesets.capturechain.CaptureChainModule;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CapturesWithKing implements CaptureChainModule {

    public static UUID id = UUID.fromString("b529904f-f115-4e57-b5ff-0f23caaeef5e");

    @Override
    public UUID getId() {
        return CapturesWithKing.id;
    }

    public CapturesWithKing() {
        this.captureWithKing = false;
    }

    private CapturesWithKing(boolean captureWithKing) {
        this.captureWithKing = captureWithKing;
    }

    @Override
    public boolean continueExtending() {
        return true;
    }

    @Override
    public List<CaptureChain> filter(List<CaptureChain> captureChains) {
        List<CaptureChain> capturesWithKing = captureChains.stream()
                .filter(captureChain -> module(captureChain).captureWithKing)
                .collect(Collectors.toList());

        return capturesWithKing.size() > 0 ? capturesWithKing : captureChains;
    }

    private CapturesWithKing module(CaptureChain captureChain) {
        return (CapturesWithKing)  captureChain.getModule(getId());
    }

    @Override
    public CaptureChainModule extend(CaptureChainModule module) {
        return new CapturesWithKing(this.captureWithKing);
    }

    @Override
    public CaptureChainModule initialize(Board board, CaptureMove move) {
        return new CapturesWithKing(board.getPiece(move.getFrom()).isKing());
    }

    private final boolean captureWithKing;
}
