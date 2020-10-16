package lt.vilniustech.side;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Piece;
import lt.vilniustech.utils.iterator.CoordinateIterator;

import java.util.ArrayList;
import java.util.List;

public class Side {

    public static final Side DRAW = new Side("Draw");

    private Side(String name) {
        this.name = name;
        this.piecePositionValidator = null;
        this.kingRowValidator = null;
        this.pieceFactory = null;
    }

    public Side(String name, CoordinateValidator piecePositionValidator, CoordinateValidator kingRowValidator, PieceFactory pieceFactory) {
        this.name = name;
        this.piecePositionValidator = piecePositionValidator;
        this.kingRowValidator = kingRowValidator;
        this.pieceFactory = pieceFactory;
        this.pieceFactory.setSide(this);
    }

    public Side getNext() {
        return next;
    }
    public void setNext(Side next) {
        this.next = next;
    }

    public void fillBoard(Board board) {
        if(piecePositionValidator == null || pieceFactory == null)
            return;

        for(Coordinate coordinate : new CoordinateIterator(board.getBoardSize())) {
            if(piecePositionValidator.isValid(coordinate)) {
                board.putPiece(coordinate, pieceFactory.producePiece());
            }
        }
    }

    public List<Piece> getPieces(Board board) {
        ArrayList<Piece> pieces = new ArrayList<>();

        if(pieceFactory == null)
            return pieces;

        for(Coordinate coordinate : new CoordinateIterator(board.getBoardSize())) {
            Piece piece = board.getPiece(coordinate);
            if(piece == null)
                continue;

            if(pieceFactory.ourProduct(piece)) {
                pieces.add(piece);
            }
        }

        return pieces;
    }

    public boolean isKingRow(Coordinate coordinate) {
        return kingRowValidator != null && kingRowValidator.isValid(coordinate);
    }

    @Override
    public String toString() {
        return name;
    }

    private final String name;
    private final CoordinateValidator piecePositionValidator;
    private final CoordinateValidator kingRowValidator;
    private final PieceFactory pieceFactory;
    private Side next;
}
