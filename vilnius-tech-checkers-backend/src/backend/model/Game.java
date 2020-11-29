package backend.model;

import api.dto.*;
import backend.tokens.TokenGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {

    public Game(GameToken gameToken, List<backend.side.Side> sides) {
        this.gameToken = gameToken;

        this.seats = new Seat[sides.size()];
        for(var i = 0; i < sides.size(); i++) {
            this.seats[i] = new Seat(new Side(sides.get(i).getIndex()));
        }
    }

    public GameToken getGameToken() {
        return gameToken;
    }

    public Seat getSeat(Side side) {
        for(var seat: seats) {
            if(Objects.equals(seat.getSide(), side)) {
                return seat;
            }
        }
        return null;
    }

    public Seat join(String name) {
        for(var seat: seats) {
            if(!seat.isOccupied()) {
                seat.takeSeat(name);
                return seat;
            }
        }
        return null;
    }

    public void leave(PlayerToken token) {
        for(var seat: seats) {
            if(seat.isOccupied()) {
                seat.standUp(token);
            }
        }
    }

    public boolean hasSeats() {
        for(var seat: seats) {
            if(!seat.isOccupied()) {
               return true;
            }
        }
        return false;
    }

    public List<Player> getPlayers() {
        var players = new ArrayList<Player>();
        for(var seat: seats) {
            if(seat.isOccupied()) {
                players.add(seat.getPlayer());
            }
        }

        return players;
    }

    private final GameToken gameToken;
    private final Seat[] seats;
}
