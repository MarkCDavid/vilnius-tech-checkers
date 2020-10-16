//package backend.rulesets.italian;
//
//import backend.Board;
//import lt.vilniustech.Side;
//import backend.moves.Move;
//import backend.rulesets.capturechain.CaptureChain;
//import backend.rulesets.capturechain.CaptureChainBuilder;
//import backend.rulesets.CaptureConstraints;
//import backend.rulesets.Filters;
//import backend.rulesets.capturechainmodules.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class ItalianCheckersCaptureConstraints implements CaptureConstraints {
//
//    public ItalianCheckersCaptureConstraints(backend.Board board, Move move) {
//        this.board = board;
//        this.captureChainBuilder = new CaptureChainBuilder();
//        this.captureChainBuilder.registerModule(ManCannotCaptureKing.id);
//        this.captureChainBuilder.registerModule(MaxCaptures.id);
//        this.captureChainBuilder.registerModule(CapturesWithKing.id);
//        this.captureChainBuilder.registerModule(MaxKingCaptures.id);
//        this.captureChainBuilder.registerModule(EarliestKingCapture.id);
//    }
//
//    @Override
//    public List<Move> filterMoves(List<Move> availableMoves) {
//        List<Move> captureMoves = availableMoves.stream()
//                .filter(Filters.captureMoves())
//                .collect(Collectors.toList());
//
//        if(captureMoves.size() > 0) {
//            List<CaptureChain> captureChains = captureChainBuilder.build(board, captureMoves);
//            if(captureChains.size() > 1) captureChains = captureChainBuilder.getModule(MaxCaptures.id).filter(captureChains);
//            if(captureChains.size() > 1) captureChains = captureChainBuilder.getModule(CapturesWithKing.id).filter(captureChains);
//            if(captureChains.size() > 1) captureChains = captureChainBuilder.getModule(MaxKingCaptures.id).filter(captureChains);
//            if(captureChains.size() > 1) captureChains = captureChainBuilder.getModule(EarliestKingCapture.id).filter(captureChains);
//            return captureChains.stream().map(CaptureChain::getMove).collect(Collectors.toList());
//        }
//        else {
//            return availableMoves;
//        }
//    }
//
//    @Override
//    public Side getNextSide(Side backend.side) {
//        return multipleCaptures ? backend.side : Side.opposite(backend.side);
//    }
//
//    @Override
//    public void setMultipleCaptures(boolean multipleCaptures) {
//        this.multipleCaptures = multipleCaptures;
//    }
//
//    private boolean multipleCaptures;
//
//    private final CaptureChainBuilder captureChainBuilder;
//    private final backend.Board board;
//}
