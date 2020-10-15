package lt.vilniustech.manager.exceptions;

public class GameFinishedException  extends RuntimeException {
    public GameFinishedException(String winner) {
        super(String.format("The game is finished with '%s' as a winner!", winner));
        this.winner = winner;
    }

    private final String winner;

    public String getWinner() {
        return winner;
    }
}
