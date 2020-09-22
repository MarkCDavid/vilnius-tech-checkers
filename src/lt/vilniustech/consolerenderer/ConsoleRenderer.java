package lt.vilniustech.consolerenderer;

import lt.vilniustech.*;
import lt.vilniustech.rulesets.CheckersRuleset;

public class ConsoleRenderer implements Renderer {

    private static final char EMPTY_CELL = ' ';
    private static final char WHITE_PIECE = '⛀';
    private static final char WHITE_KING = '⛁';
    private static final char BLACK_PIECE = '⛂';
    private static final char BLACK_KING = '⛃';

    @Override
    public void render(Board board) {
        CheckersRuleset ruleset = board.getRuleset();
        Buffer buffer = new Buffer(ruleset.getBoardSize());

        for(int row = 0; row < ruleset.getBoardSize(); row++) {
            for(int column = 0; column < ruleset.getBoardSize(); column++) {
                Coordinate coordinate = new Coordinate(column, row);
                char symbol = getSymbol(board.getCell(coordinate).getPiece());
                buffer.fillShape(Shapes.getShape(row, column, ruleset.getBoardSize()), row, column, symbol );
            }
        }

        System.out.println(buffer.toString());
    }

    private char getSymbol(Piece piece) {
        if (piece == null) {
            return EMPTY_CELL;
        } else if (piece.getSide() == Side.BLACK) {
            return piece.isKing() ? BLACK_KING : BLACK_PIECE;
        } else if (piece.getSide() == Side.WHITE) {
            return piece.isKing() ? WHITE_KING : WHITE_PIECE;
        } else {
            throw new IllegalStateException();
        }
    }
}