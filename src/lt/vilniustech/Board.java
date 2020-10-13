package lt.vilniustech;

import lt.vilniustech.rulesets.*;

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
  }

  public Piece getPiece(Coordinate coordinate) {
    if (!validCoordinate(coordinate)) return null;

    return this.pieceMap.getOrDefault(coordinate, null);
  }

  public Piece popPiece(Coordinate coordinate) {
    if (!validCoordinate(coordinate)) return null;

    Piece piece = this.pieceMap.getOrDefault(coordinate, null);
    this.pieceMap.remove(coordinate);
    return piece;
  }

  public void swapPieces(Coordinate coordinate1, Coordinate coordinate2) {
    Piece piece1 = getPiece(coordinate1);
    Piece piece2 = getPiece(coordinate2);

    if (piece1 == null || piece2 == null)
      return;

    putPiece(coordinate1, piece2);
    putPiece(coordinate2, piece1);
  }

  public boolean validCoordinate(Coordinate coordinate) {
    boolean invalidX =
        coordinate.getColumn() < 0 || coordinate.getColumn() >= boardSize;
    if (invalidX) return false;

    boolean invalidY =
        coordinate.getColumn() < 0 || coordinate.getColumn() >= boardSize;
    if (invalidY) return false;

    int index = coordinate.getIndex(boardSize);
    return index >= 0 && index < boardSize * boardSize;
  }

  private final int boardSize;
  private final Map<Coordinate, Piece> pieceMap;
}
