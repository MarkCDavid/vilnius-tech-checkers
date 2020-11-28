package api.endpoints;

import api.dto.Ruleset;

public interface GameLaunchService {

    GameService launch(Ruleset ruleset);

}
