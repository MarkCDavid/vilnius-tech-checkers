package lt.vilniustech.manager.exceptions;

import lt.vilniustech.moves.base.Move;

public class IllegitimateMoveException extends RuntimeException {

    public IllegitimateMoveException(Move move) {
        super(String.format("The given move '%s' is illegitimate!", move));
    }
}