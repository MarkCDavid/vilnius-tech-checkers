//package lt.vilniustech.manager.state;
//
//import lt.vilniustech.*;
//import lt.vilniustech.moves.CaptureMove;
//import lt.vilniustech.moves.Move;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class StateMachine {
//
//    public StateMachine(State initialState) {
//        this.currentState = initialState;
//    }
//
//    public void performMove(Board board, Move move) {
//        this.currentState = currentState.performMove(board, move);
//    }
//
//    public List<CaptureMove> getPerformedMoves() {
//        if(this.currentState instanceof CaptureState)
//            return ((CaptureState)this.currentState).getCaptureMoves();
//        return new ArrayList<>();
//    }
//
//    public Side getNextSide(Side currentSide) {
//        return currentState.getNextSide(currentSide);
//    }
//
//    public boolean isMultiCapture() { return currentState.isMultiCapture(); }
//
//    private State currentState;
//
//}
