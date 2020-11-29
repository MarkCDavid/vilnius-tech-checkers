package api.dto;

public class GameToken {

    public GameToken(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private final String value;
}
