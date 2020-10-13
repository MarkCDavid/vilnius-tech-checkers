package lt.vilniustech.side;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Piece;
import lt.vilniustech.utils.CoordinateIterator;

import java.util.ArrayList;
import java.util.List;

public class Side {

    public Side(String name, PieceSetter cellFill, PieceFactory pieceFactory) {
        this.name = name;
        this.pieceSetter = cellFill;
        this.pieceFactory = pieceFactory;
    }

    public void fillBoard(Board board) {
        for(Coordinate coordinate : new CoordinateIterator(board.getBoardSize())) {
            if(pieceSetter.setPiece(coordinate)) {
                board.putPiece(coordinate, pieceFactory.producePiece());
            }
        }
    }

    public List<Piece> getPieces(Board board) {
        ArrayList<Piece> pieces = new ArrayList<>();

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

    @Override
    public String toString() {
        return name;
    }

    private final String name;
    private final PieceSetter pieceSetter;
    private final PieceFactory pieceFactory;
}
