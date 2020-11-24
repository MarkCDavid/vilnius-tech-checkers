package backend.factory;

import backend.rulesets.CheckersRuleset;
import backend.rulesets.english.EnglishCheckers;
import backend.rulesets.fourplayer.FourPlayerCheckers;
import backend.rulesets.turkish.TurkishCheckers;

import java.util.UUID;

public class RulesetFactory {

    public static CheckersRuleset build(UUID oid) {
        if (EnglishCheckers.OID.equals(oid))
            return new EnglishCheckers();
        else if (TurkishCheckers.OID.equals(oid))
            return new TurkishCheckers();
        else if (FourPlayerCheckers.OID.equals(oid))
            return new FourPlayerCheckers();

        throw new IllegalStateException();
    }

    private RulesetFactory() { }
}
