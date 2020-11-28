package api.endpoints;

import api.dto.Move;

public interface GameMoveService {

    void apply(Move move);
}
