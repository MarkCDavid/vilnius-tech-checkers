package api.endpoints;

import api.dto.Coordinate;
import api.dto.GameToken;
import api.dto.Move;
import api.dto.PlayerToken;

import java.util.List;

public interface GameInteractionService {

    List<Coordinate> getInteractable(GameToken game, PlayerToken player);
    List<Move> getMoves(GameToken game, PlayerToken player, Coordinate from);
    boolean applyMove(GameToken game, PlayerToken player, Move move);

}
