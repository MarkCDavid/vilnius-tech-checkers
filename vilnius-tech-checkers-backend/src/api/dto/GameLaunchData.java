package api.dto;

import java.util.Map;

public class GameLaunchData {

    private final GameToken game;
    private final Map<Side, PlayerToken> players;

    public GameLaunchData(GameToken game, Map<Side, PlayerToken> players) {
        this.game = game;
        this.players = players;
    }

    public GameToken getGame() {
        return game;
    }

    public Map<Side, PlayerToken> getPlayers() {
        return players;
    }
}
