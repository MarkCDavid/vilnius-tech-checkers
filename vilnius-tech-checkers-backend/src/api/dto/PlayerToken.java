package api.dto;

import java.util.Objects;

public class PlayerToken {

    public PlayerToken(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private final String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerToken that = (PlayerToken) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
