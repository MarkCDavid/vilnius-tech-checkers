package api.dto;

import java.util.Objects;

public class Side {

    public Side(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    private final Integer value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Side side = (Side) o;
        return value.equals(side.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
