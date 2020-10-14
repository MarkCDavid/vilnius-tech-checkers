package lt.vilniustech;

import java.util.*;

public class Board {

    public int getBoardSize() {
        return boardSize;
    }

    public Board() {
        this(0);
    }

    public Board(int boardSize) {
        this.pieceMap = new HashMap<>();
        this.boardSize = boardSize;
    }

    public void putPiece(Coordinate coordinate, Piece piece) {
        if (!validCoordinate(coordinate)) return;

        this.pieceMap.put(coordinate, piece);

        if(piece != null)
            piece.setCoordinate(coordinate);
    }

    public Piece popPiece(Coordinate coordinate) {
        if (!validCoordinate(coordinate)) return null;

        Piece piece = getPiece(coordinate);
        this.pieceMap.remove(coordinate);

        if(piece != null)
            piece.setCoordinate(null);

        return piece;
    }

    public Piece getPiece(Coordinate coordinate) {
        if (!validCoordinate(coordinate)) return null;

        return this.pieceMap.getOrDefault(coordinate, null);
    }

    public void movePiece(Coordinate from, Coordinate to) {
        Piece fromPiece = getPiece(from);
        Piece toPiece = getPiece(to);

        if(fromPiece == null || toPiece != null)
            return;

        putPiece(to, popPiece(from));
    }

    public boolean validCoordinate(Coordinate coordinate) {
        boolean invalidX = coordinate.getColumn() < 0 || coordinate.getColumn() >= boardSize;
        if (invalidX) return false;

        boolean invalidY = coordinate.getColumn() < 0 || coordinate.getColumn() >= boardSize;
        if (invalidY) return false;

        int index = coordinate.getIndex(boardSize);
        return index >= 0 && index < boardSize * boardSize;
    }

    private final int boardSize;
    private final Map<Coordinate, Piece> pieceMap;
}
