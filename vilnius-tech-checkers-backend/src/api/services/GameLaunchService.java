package api.services;

import api.dto.Ruleset;
import api.endpoints.GameService;
import backend.factory.RulesetFactory;
import backend.manager.GameManager;

public class GameLaunchService implements api.endpoints.GameLaunchService {
    @Override
    public GameService launch(Ruleset ruleset) {
        var concreteRuleset = RulesetFactory.build(ruleset.getId());
        return new api.services.GameService(new GameManager(concreteRuleset));
    }
}
