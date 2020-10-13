package lt.vilniustech.manager;

import lt.vilniustech.side.Side;

public class GameFinishedException  extends RuntimeException {
    public GameFinishedException(Side winner) {
        super(String.format("The game is finished with '%s' as a winner!", winner));
        this.winner = winner;
    }

    private final Side winner;

    public Side getWinner() {
        return winner;
    }
}
