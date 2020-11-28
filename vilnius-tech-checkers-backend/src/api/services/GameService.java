package api.services;

import api.dto.Cell;
import api.dto.Coordinate;
import api.dto.Move;
import api.serializer.concrete.CoordinateSerializer;
import api.serializer.concrete.MoveSerializer;
import api.serializer.concrete.PieceSerializer;
import backend.manager.GameManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameService implements api.endpoints.GameService {

    private final GameManager gameManager;
    private final CoordinateSerializer coordinateSerializer;
    private final PieceSerializer pieceSerializer;
    private final MoveSerializer moveSerializer;

    public GameService(GameManager gameManager) {
        this.gameManager = gameManager;
        this.coordinateSerializer = new CoordinateSerializer();
        this.moveSerializer = new MoveSerializer();
        this.pieceSerializer = new PieceSerializer();
    }


    @Override
    public int getBoardSize() {
        return gameManager.getBoard().getBoardSize();
    }

    @Override
    public List<Cell> getBoard() {
        var dtoBoard = new ArrayList<Cell>();
        var board = gameManager.getBoard();

        for(var i = 0; i < board.getBoardSize(); i++) {
            for(var j = 0; j < board.getBoardSize(); j++) {
                var coordinate = new backend.Coordinate(i, j);
                var piece = board.getPiece(coordinate);

                dtoBoard.add(new Cell(
                        coordinateSerializer.serialize(coordinate),
                        pieceSerializer.serialize(piece)
                ));
            }
        }

        return dtoBoard;
    }

    @Override
    public List<Coordinate> getInteractable() {
        return gameManager.getAvailableMoves()
                .stream().map(move -> coordinateSerializer.serialize(move.getFrom()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Move> getAvailableMoves(Coordinate from) {
        return gameManager.getAvailableMoves(coordinateSerializer.deserialize(from))
                .stream().map(moveSerializer::serialize)
                .collect(Collectors.toList());
    }
}
