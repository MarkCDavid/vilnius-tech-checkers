package api.services.mock;

import api.dto.*;
import backend.Board;
import backend.rulesets.english.EnglishCheckers;
import java.util.List;
import java.util.stream.Collectors;

public class GameInteractionService implements api.endpoints.GameInteractionService {

    @Override
    public List<Coordinate> getInteractable(GameToken game, PlayerToken player) {
        var board = new Board(8);
        var ruleset = new EnglishCheckers();
        var playingSides = ruleset.getPlayingSides();
        playingSides.get(0).fillBoard(board);
        playingSides.get(1).fillBoard(board);
        return playingSides.get(0).getPieces(board).stream().map(piece ->
                new Coordinate(piece.getCoordinate().getRow(), piece.getCoordinate().getColumn())).collect(Collectors.toList());

    }

    @Override
    public List<Move> getMoves(GameToken game, PlayerToken player, Coordinate from) {
        return null;
    }

    @Override
    public boolean applyMove(GameToken game, PlayerToken player, Move move) {
        return false;
    }
}
