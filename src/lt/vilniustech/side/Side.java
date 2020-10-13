package lt.vilniustech.side;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Piece;
import lt.vilniustech.utils.CoordinateIterator;

import java.util.ArrayList;
import java.util.List;

public class Side {

    public Side(String name, CoordinateValidator piecePositionValidator, CoordinateValidator kingRowValidator, PieceFactory pieceFactory) {
        this.name = name;
        this.piecePositionValidator = piecePositionValidator;
        this.kingRowValidator = kingRowValidator;
        this.pieceFactory = pieceFactory;
    }

    public Side getNext() {
        return next;
    }

    public void setNext(Side next) {
        this.next = next;
    }



    public void fillBoard(Board board) {
        for(Coordinate coordinate : new CoordinateIterator(board.getBoardSize())) {
            if(piecePositionValidator.isValid(coordinate)) {
                board.putPiece(coordinate, pieceFactory.producePiece(this));
            }
        }
    }

    public List<Piece> getPieces(Board board) {
        ArrayList<Piece> pieces = new ArrayList<>();

        for(Coordinate coordinate : new CoordinateIterator(board.getBoardSize())) {
            Piece piece = board.getPiece(coordinate);
            if(piece == null)
                continue;

            if(pieceFactory.ourProduct(this, piece)) {
                pieces.add(piece);
            }
        }

        return pieces;
    }

    public boolean isKingRow(Coordinate coordinate) {
        return kingRowValidator.isValid(coordinate);
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
