//package lt.vilniustech.manager.state;
//
//import lt.vilniustech.Board;
//import lt.vilniustech.Side;
//import lt.vilniustech.manager.AvailableMovesBuilder;
//import lt.vilniustech.moves.CaptureMove;
//import lt.vilniustech.moves.Move;
//import lt.vilniustech.rulesets.CaptureConstraints;
//import lt.vilniustech.rulesets.Filters;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public abstract class State {
//
//    public abstract State performMove(Board board, Move move);
//    public abstract Side getNextSide(Side currentSide);
//    public abstract boolean isMultiCapture();
//
//    protected static boolean hasCaptureMoves(Board board, Move move, Side side, List<CaptureMove> unavailableMoves) {
//        CaptureConstraints captureConstraints = board.getRuleset().getCaptureConstraints(board, move);
//        captureConstraints.setMultipleCaptures(true);
//        List<Move> availableMoves = captureConstraints.filterMoves(new AvailableMovesBuilder(board).buildAvailableMoves());
//        List<Move> availableCaptureMoves = availableMoves.stream().filter(Filters.captureMoves()).collect(Collectors.toList());
//        return availableCaptureMoves.size() > 0;
//    }
//
//    protected static Side getSide(Board board, Move move) {
//        return board.getPiece(move.getFrom()).getSide();
//    }
//}
