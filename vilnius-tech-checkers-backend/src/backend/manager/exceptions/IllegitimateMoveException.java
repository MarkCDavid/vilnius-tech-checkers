package backend.manager.exceptions;

import backend.moves.base.Move;

public class IllegitimateMoveException extends RuntimeException {

    public IllegitimateMoveException(Move move) {
        super(String.format("The given move '%s' is illegitimate!", move));
    }
}