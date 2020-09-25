package lt.vilniustech.rulesets.capturechainmodules;

import lt.vilniustech.Board;
import lt.vilniustech.Piece;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.rulesets.capturechain.CaptureChain;
import lt.vilniustech.rulesets.capturechain.CaptureChainModule;

import java.util.List;
import java.util.UUID;

public class ManCannotCaptureKing implements CaptureChainModule {

    public static UUID id = UUID.fromString("180a7783-433d-494e-8572-e75e63b2b347");

    @Override
    public UUID getId() {
        return ManCannotCaptureKing.id;
    }

    public ManCannotCaptureKing() {
        this.manCapturesKing = false;
    }

    private ManCannotCaptureKing(boolean manCapturesKing) {
        this.manCapturesKing = manCapturesKing;
    }

    @Override
    public boolean continueExtending() {
        return !manCapturesKing;
    }

    @Override
    public List<CaptureChain> filter(List<CaptureChain> captureChains) {
        return captureChains;
    }

    @Override
    public CaptureChainModule extend(CaptureChainModule module) {
        if(module instanceof ManCannotCaptureKing) {
            ManCannotCaptureKing other = (ManCannotCaptureKing)module;
            return new ManCannotCaptureKing(manCapturesKing || other.manCapturesKing);
        }
        return new ManCannotCaptureKing(manCapturesKing);
    }

    @Override
    public CaptureChainModule initialize(Board board, CaptureMove move) {
        Piece fromPiece = board.getCell(move.getFrom()).getPiece();
        Piece overPiece = board.getCell(move.getOver()).getPiece();
        return new ManCannotCaptureKing(!fromPiece.isKing() && overPiece.isKing());
    }

    private final boolean manCapturesKing;
}
