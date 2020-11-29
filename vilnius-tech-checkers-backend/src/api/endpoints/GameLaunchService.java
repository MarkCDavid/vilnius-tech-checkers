package api.endpoints;

import api.dto.GameLaunchData;
import api.dto.GameToken;
import api.dto.PlayerToken;
import api.dto.Ruleset;

import java.util.List;

public interface GameLaunchService {
    GameLaunchData launchLocal(Ruleset ruleset, List<String> names);
    GameLaunchData launchRemote(Ruleset ruleset, String name);

    GameLaunchData joinGame(GameToken game, String name);
    void leaveGame(GameToken game, PlayerToken player);
}
