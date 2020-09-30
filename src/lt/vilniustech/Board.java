package lt.vilniustech;

import lt.vilniustech.moves.*;
import lt.vilniustech.rulesets.*;

import java.util.*;

public class Board {

  public CheckersRuleset getRuleset() {
    return ruleset;
  }

  public Map<Coordinate, Piece> getPieceMap() {
    return pieceMap;
  }

  public Board() {
    this.pieceMap = new HashMap<>();
    this.ruleset = null;
  }

  public Board(CheckersRuleset ruleset) {
    this.ruleset = ruleset;
    this.pieceMap = new HashMap<>();

    for (int row = 0; row < ruleset.getBoardSize(); row++) {
      for (int column = 0; column < ruleset.getBoardSize(); column++) {

        Coordinate coordinate = new Coordinate(column, row);

        if (ruleset.getCellFill(Side.BLACK).fillCell(coordinate))
          putPiece(coordinate, ruleset.createPiece(Side.BLACK));

        if (ruleset.getCellFill(Side.WHITE).fillCell(coordinate))
          putPiece(coordinate, ruleset.createPiece(Side.WHITE));
      }
    }
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

  public boolean applyMove(Move move) {
    Piece piece = getPiece(move.getFrom());
    Side side = piece.getSide();
    boolean destinationIsKingRow = ruleset.isKingRow(side, move.getTo());

    move.apply(this);
    if (ruleset.isPromotionImmediate() && destinationIsKingRow && !piece.isKing()) {
      Piece kingPiece = ruleset.createKing(side);
      putPiece(move.getTo(), kingPiece);
      return false;
    }
    return true;
  }

  public List<Piece> getSidePieces(Side side) {
    ArrayList<Piece> pieces = new ArrayList<>();
    for (Piece piece : pieceMap.values()) {
      if (piece != null && piece.getSide() == side) pieces.add(piece);
    }
    return pieces;
  }

  public boolean validCoordinate(Coordinate coordinate) {
    boolean invalidX =
        coordinate.getColumn() < 0 || coordinate.getColumn() >= ruleset.getBoardSize();
    if (invalidX) return false;

    boolean invalidY =
        coordinate.getColumn() < 0 || coordinate.getColumn() >= ruleset.getBoardSize();
    if (invalidY) return false;

    int index = coordinate.getIndex(ruleset.getBoardSize());
    return index >= 0 && index < ruleset.getBoardSize() * ruleset.getBoardSize();
  }

  private final Map<Coordinate, Piece> pieceMap;
  private final CheckersRuleset ruleset;
}
