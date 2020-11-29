package backend.model;

import api.dto.Player;
import api.dto.PlayerToken;
import api.dto.Side;
import backend.tokens.TokenGenerator;

import java.util.Objects;

public class Seat {

    public Seat(Side side) {
        this.side = side;
    }

    public Side getSide() {
        return side;
    }

    public PlayerToken getToken() {
        return token;
    }

    public boolean isOccupied() {
        return token != null;
    }

    public PlayerToken takeSeat(String name) {
        if(isOccupied())
            return null;

        this.name = name;
        this.token = new PlayerToken(TokenGenerator.generateTokenValue());
        return this.token;
    }

    public void standUp(PlayerToken token) {
        if(!Objects.equals(this.token, token))
            return;

        this.name = null;
        this.token = null;
    }

    public Player getPlayer() {
        return new Player(side, name);
    }

    private String name;
    private PlayerToken token;
    private final Side side;
}
