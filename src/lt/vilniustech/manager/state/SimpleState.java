//package lt.vilniustech.manager.state;
//
//import lt.vilniustech.Board;
//import lt.vilniustech.Side;
//import lt.vilniustech.moves.CaptureMove;
//import lt.vilniustech.moves.Move;
//import lt.vilniustech.moves.SimpleMove;
//
//import java.util.ArrayList;
//
//public class SimpleState extends State {
//
//    @Override
//    public State performMove(Board board, Move move) {
//        Side side = getSide(board, move);
//        move.apply(board);
//
//        if(move instanceof SimpleMove) return this;
//        if(move instanceof CaptureMove) {
//            ArrayList<CaptureMove> unavailable = new ArrayList<>();
//            unavailable.add((CaptureMove) move);
//            return hasCaptureMoves(board, move, side, unavailable) ? new CaptureState(board, (CaptureMove) move) : this;
//        }
//
//        throw new IllegalStateException();
//    }
//
//    @Override
//    public Side getNextSide(Side currentSide) {
//        return Side.opposite(currentSide);
//    }
//
//    @Override
//    public boolean isMultiCapture() {
//        return false;
//    }
//
//
//}
