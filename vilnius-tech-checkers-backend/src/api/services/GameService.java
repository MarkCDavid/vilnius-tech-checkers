package api.services;

import api.dto.*;
import api.endpoints.GameDataService;
import api.endpoints.GameInteractionService;
import api.endpoints.GameLaunchService;
import api.event.OnChangeEventListener;
import api.serializer.concrete.CoordinateSerializer;
import api.serializer.concrete.PieceSerializer;
import backend.factory.RulesetFactory;
import backend.manager.GameManager;
import backend.manager.GameManager2;
import backend.model.Game;
import backend.tokens.TokenGenerator;
import backend.utils.iterator.CoordinateIterator;

import java.util.*;

public class GameService implements GameLaunchService, GameDataService, GameInteractionService {

    private final Map<GameToken, Game> games;
    private final Map<GameToken, GameManager2> gameManagers;

    private final PieceSerializer pieceSerializer;
    private final CoordinateSerializer coordinateSerializer;


    public GameService() {
        games = new HashMap<>();
        gameManagers = new HashMap<>();
        pieceSerializer = new PieceSerializer();
        coordinateSerializer = new CoordinateSerializer();
    }

    @Override
    public BoardData getBoardData(GameToken gameToken) {
        var gameManager = gameManagers.getOrDefault(gameToken, null);
        if(gameManager == null)
            return new BoardData(-1, -1);

        var size = gameManager.getBoard().getBoardSize();
        return new BoardData(size, size * size);
    }

    @Override
    public List<Player> getPlayers(GameToken gameToken) {
        var game = games.getOrDefault(gameToken, null);
        return game != null ? game.getPlayers() : new ArrayList<>();
    }

    @Override
    public List<Cell> getCells(GameToken gameToken) {
        var cells = new ArrayList<Cell>();
        var gameManager = gameManagers.getOrDefault(gameToken, null);
        if(gameManager == null)
            return cells;

        for(var coordinate: new CoordinateIterator(gameManager.getBoard().getBoardSize())) {
            var piece = pieceSerializer.serialize(gameManager.getBoard().getPiece(coordinate));
            cells.add(new Cell(coordinateSerializer.serialize(coordinate), piece));
        }

        return cells;
    }

    @Override
    public List<Move> getMoveHistory(GameToken gameToken) {
        return new ArrayList<>();
    }

    @Override
    public Side getCurrentSide(GameToken gameToken) {
        var gameManager = gameManagers.getOrDefault(gameToken, null);
        if(gameManager == null)
            return null;

        return new Side(gameManager.getCurrentSide().getIndex());
    }

    @Override
    public void subscribeOnChangeListener(GameToken game, OnChangeEventListener listener) {

    }

    @Override
    public void unsubscribeOnChangeListener(GameToken game, OnChangeEventListener listener) {

    }

    @Override
    public List<Coordinate> getInteractable(GameToken gameToken, PlayerToken playerToken) {
        var result = new ArrayList<Coordinate>();

        var gameManager = gameManagers.getOrDefault(gameToken, null);
        if(gameManager == null)
            return result;


        var game = games.getOrDefault(gameToken, null);
        if(game == null)
            return result;


        var currentSide = gameManager.getCurrentSide();
        var side = new Side(currentSide.getIndex());
        var seat = game.getSeat(side);

        if(seat == null)
            return result;

        if(!Objects.equals(seat.getToken(), playerToken))
            return result;

        for(var move: gameManager.getAvailableMoves()) {
            result.add(coordinateSerializer.serialize(move.getFrom()));
        }

        return result;
    }

    @Override
    public List<Move> getMoves(GameToken gameToken, PlayerToken playerToken, Coordinate from) {
        var result = new ArrayList<Move>();

        var gameManager = gameManagers.getOrDefault(gameToken, null);
        if(gameManager == null)
            return result;


        var game = games.getOrDefault(gameToken, null);
        if(game == null)
            return result;


        var currentSide = gameManager.getCurrentSide();
        var side = new Side(currentSide.getIndex());
        var seat = game.getSeat(side);

        if(seat == null)
            return result;

        if(!Objects.equals(seat.getToken(), playerToken))
            return result;

        for(var move: gameManager.getAvailableMoves(coordinateSerializer.deserialize(from))) {
            result.add(
                    new Move(
                            coordinateSerializer.serialize(move.getFrom()),
                            coordinateSerializer.serialize(move.getTo())
                    )
            );
        }

        return result;
    }

    @Override
    public boolean applyMove(GameToken gameToken, PlayerToken playerToken, Move move) {

        var gameManager = gameManagers.getOrDefault(gameToken, null);
        if(gameManager == null)
            return false;


        var game = games.getOrDefault(gameToken, null);
        if(game == null)
            return false;


        var currentSide = gameManager.getCurrentSide();
        var side = new Side(currentSide.getIndex());
        var seat = game.getSeat(side);

        if(seat == null)
            return false;

        if(!Objects.equals(seat.getToken(), playerToken))
            return false;

        for(var availableMove: gameManager.getAvailableMoves()) {
            var coordinateFrom = coordinateSerializer.serialize(availableMove.getFrom());
            if(!Objects.equals(move.getFrom(), coordinateFrom))
                continue;

            var coordinateTo  = coordinateSerializer.serialize(availableMove.getTo());
            if(!Objects.equals(move.getTo(), coordinateTo))
                continue;

            gameManager.processMove(availableMove);

            return true;
        }

        return false;
    }

    @Override
    public GameLaunchData launchLocal(Ruleset ruleset, List<String> names) {
        var players = new HashMap<Side, PlayerToken>();
        var game = launchGame(ruleset);

        for(var name: names) {
            var seat = game.join(name);
            if(seat == null)
                break;

            players.put(seat.getSide(), seat.getToken());
        }

        return new GameLaunchData(game.getGameToken(), players);
    }

    @Override
    public GameLaunchData launchRemote(Ruleset ruleset, String name) {
        var game = launchGame(ruleset);
        var seat = game.join(name);

        return new GameLaunchData(game.getGameToken(), Map.of(seat.getSide(), seat.getToken()));
    }

    @Override
    public GameLaunchData joinGame(GameToken gameToken, String name) {
        var game = games.getOrDefault(gameToken, null);
        if(game == null)
            return null;

        var seat = game.join(name);
        if(seat == null)
            return null;

        return new GameLaunchData(game.getGameToken(), Map.of(seat.getSide(), seat.getToken()));
    }

    @Override
    public void leaveGame(GameToken gameToken, PlayerToken playerToken) {
        var game = games.getOrDefault(gameToken, null);
        if(game == null)
            return;

        game.leave(playerToken);
    }

    private Game launchGame(Ruleset ruleset) {

        var gameToken = new GameToken(TokenGenerator.generateTokenValue());

        var gameManager = new GameManager2(RulesetFactory.build(ruleset.getId()));
        var game = new Game(gameToken, gameManager.getPlayingSides());

        games.put(gameToken, game);
        gameManagers.put(gameToken, gameManager);
        return game;
    }
}
