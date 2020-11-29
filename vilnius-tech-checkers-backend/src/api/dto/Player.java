package api.dto;

public class Player {

    private final Side side;
    private final String name;

    public Player(Side side, String name) {
        this.side = side;
        this.name = name;
    }

    public Side getSide() {
        return side;
    }

    public String getName() {
        return name;
    }
}
