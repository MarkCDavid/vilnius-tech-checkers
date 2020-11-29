package api.services.mock;

import api.dto.*;
import api.endpoints.GameDataService;
import api.event.OnChangeEventListener;
import backend.Board;
import backend.rulesets.english.EnglishCheckers;
import backend.rulesets.english.EnglishCheckersPieceFactory;
import backend.utils.iterator.CoordinateIterator;

import java.util.ArrayList;
import java.util.List;

public class GameDataSerivce implements GameDataService {


    @Override
    public BoardData getBoardData(GameToken game) {
        return new BoardData(8, 64);
    }

    @Override
    public List<Player> getPlayers(GameToken game) {
        return null;
    }

    @Override
    public List<Cell> getCells(GameToken game) {
        var board = new Board(8);
        var ruleset = new EnglishCheckers();
        var playingSides = ruleset.getPlayingSides();

        playingSides.get(0).fillBoard(board);
        playingSides.get(1).fillBoard(board);

        var cells = new ArrayList<Cell>();

        for(var coordinate: new CoordinateIterator(8)) {
            var piece = board.getPiece(coordinate);
            Piece dtoPiece = null;
            if(piece != null)
                dtoPiece = new Piece(new Side(piece.getSide().getIndex()), piece.getPromotionLevel());


            cells.add(new Cell(
                    new Coordinate(coordinate.getRow(), coordinate.getColumn()),
                    dtoPiece
            ));
        }

        return cells;
    }

    @Override
    public List<Move> getMoveHistory(GameToken game) {
        return null;
    }

    @Override
    public Side getCurrentSide(GameToken game) {
        return new Side(1);
    }

    @Override
    public void subscribeOnChangeListener(GameToken game, OnChangeEventListener listener) {
    }

    @Override
    public void unsubscribeOnChangeListener(GameToken game, OnChangeEventListener listener) {

    }
}
