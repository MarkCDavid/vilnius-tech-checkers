//package backend.rulesets.international;
//
//import backend.Board;
//import lt.vilniustech.Side;
//import backend.moves.Move;
//import backend.rulesets.CaptureConstraints;
//import backend.rulesets.Filters;
//import backend.rulesets.capturechain.CaptureChain;
//import backend.rulesets.capturechain.CaptureChainBuilder;
//import backend.rulesets.capturechainmodules.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class InternationalCheckersCaptureConstraints implements CaptureConstraints {
//
//    public InternationalCheckersCaptureConstraints(backend.Board board, Move move) {
//        this.board = board;
//        this.captureChainBuilder = new CaptureChainBuilder();
//        this.captureChainBuilder.registerModule(MaxCaptures.id);
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
