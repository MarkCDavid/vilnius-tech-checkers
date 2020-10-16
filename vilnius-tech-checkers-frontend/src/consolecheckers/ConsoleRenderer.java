package consolecheckers;

import backend.Board;
import backend.Coordinate;
import backend.Piece;

public class ConsoleRenderer  {

    private static final char EMPTY_CELL = ' ';
    private static final char WHITE_PIECE = '⛀';
    private static final char WHITE_KING = '⛁';
    private static final char BLACK_PIECE = '⛂';
    private static final char BLACK_KING = '⛃';

    public void render(Board board) {
        Buffer buffer = new Buffer(board.getBoardSize());

        for(int row = 0; row < board.getBoardSize(); row++) {
            for(int column = 0; column < board.getBoardSize(); column++) {
                Coordinate coordinate = new Coordinate(column, row);
                char symbol = getSymbol(board.getPiece(coordinate));
                buffer.fillShape(Shapes.getShape(row, column, board.getBoardSize()), row, column, symbol );
            }
        }

        System.out.println(buffer.toString());
    }

    private char getSymbol(Piece piece) {
        if (piece == null) {
            return EMPTY_CELL;
        } else if (piece.getSide().toString() == "Black") {
            return piece.isKing() ? BLACK_KING : BLACK_PIECE;
        } else if (piece.getSide().toString() == "White") {
            return piece.isKing() ? WHITE_KING : WHITE_PIECE;
        } else {
            throw new IllegalStateException();
        }
    }
}