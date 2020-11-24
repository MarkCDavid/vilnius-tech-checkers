package api.dto;

import java.util.UUID;

public class Ruleset {

    public Ruleset(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private final UUID id;

    private final String name;

    @Override
    public String toString() {
        return getName();
    }
}
