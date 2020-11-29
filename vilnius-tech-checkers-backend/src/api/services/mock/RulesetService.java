package api.services.mock;

import api.dto.Ruleset;
import backend.rulesets.english.EnglishCheckers;
import backend.rulesets.fourplayer.FourPlayerCheckers;
import backend.rulesets.turkish.TurkishCheckers;

import java.util.List;

public class RulesetService implements api.endpoints.RulesetService {
    @Override
    public List<Ruleset> getRulesets() {
        return List.of(
                new Ruleset(EnglishCheckers.OID, "English Checkers", 2),
                new Ruleset(TurkishCheckers.OID, "Turkish Checkers", 2),
                new Ruleset(FourPlayerCheckers.OID, "Four Player Checkers", 4)
                //new ItalianCheckers(),
                // new InternationalCheckers(),
        );
    }
}
