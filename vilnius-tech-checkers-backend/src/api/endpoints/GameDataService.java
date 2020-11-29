package api.endpoints;

import api.dto.*;
import api.event.OnChangeEventListener;

import java.util.List;

public interface GameDataService {

    BoardData getBoardData(GameToken game);
    List<Player> getPlayers(GameToken game);
    List<Cell> getCells(GameToken game);
    List<Move> getMoveHistory(GameToken game);
    Side getCurrentSide(GameToken game);

    void subscribeOnChangeListener(GameToken game, OnChangeEventListener listener);
    void unsubscribeOnChangeListener(GameToken game, OnChangeEventListener listener);

}
