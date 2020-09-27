package lt.vilniustech.manager.state;

import lt.vilniustech.*;
import lt.vilniustech.moves.Move;

public class StateMachine {

    public StateMachine(State initialState) {
        this.currentState = initialState;
    }

    public void performMove(Board board, Move move) {
        this.currentState = currentState.performMove(board, move);
    }

    public Side getNextSide(Side currentSide) {
        return currentState.getNextSide(currentSide);
    }

    public boolean isMultiCapture() { return currentState.isMultiCapture(); }

    private State currentState;

}
