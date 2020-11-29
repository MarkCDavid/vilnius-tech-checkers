package api.dto;

import java.util.UUID;

public class Ruleset {

    public Ruleset(UUID id, String name, Integer playerCount) {
        this.id = id;
        this.name = name;
        this.playerCount = playerCount;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPlayerCount() {
        return playerCount;
    }

    private final UUID id;
    private final String name;
    private final Integer playerCount;
}
