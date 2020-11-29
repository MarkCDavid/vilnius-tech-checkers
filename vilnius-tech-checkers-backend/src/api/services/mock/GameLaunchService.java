package api.services.mock;

import api.dto.*;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class GameLaunchService implements api.endpoints.GameLaunchService {

    @Override
    public GameLaunchData launchLocal(Ruleset ruleset, List<String> names) {
        var players = new HashMap<Side, PlayerToken>();
        for(int i = 0; i < ruleset.getPlayerCount(); i++) {
            players.put(new Side(i), new PlayerToken(String.format("PLAYER_%s", i)));
        }


        return new GameLaunchData(
                new GameToken("GAME_AAA"),
                players
        );
    }

    @Override
    public GameLaunchData launchRemote(Ruleset ruleset, String name) {
        return null;
    }

    @Override
    public GameLaunchData joinGame(GameToken game, String name) {
        return null;
    }

    @Override
    public void leaveGame(GameToken game, PlayerToken player) {

    }
}
