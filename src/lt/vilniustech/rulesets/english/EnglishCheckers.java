package lt.vilniustech.rulesets.english;

import lt.vilniustech.*;
import lt.vilniustech.rulesets.CellFill;
import lt.vilniustech.rulesets.CheckersRuleset;

public class EnglishCheckers implements CheckersRuleset {

    @Override
    public int getBoardSize() {
        return 8;
    }

    @Override
    public Piece createWhitePiece() {
        return new Piece(Side.WHITE, new Direction[]{
                new Direction(1, -1),
                new Direction(-1, -1),
        });
    }

    @Override
    public Piece createBlackPiece() {
        return new Piece(Side.BLACK, new Direction[]{
                new Direction(1, 1),
                new Direction(-1, 1),
        });
    }

    @Override
    public Piece createWhiteKing() {
        return new Piece(Side.WHITE, new Direction[]{
                new Direction(1, -1),
                new Direction(-1, -1),
                new Direction(1, 1),
                new Direction(-1, 1),
        });
    }
    @Override
    public Piece createBlackKing() {
        return new Piece(Side.BLACK, new Direction[]{
                new Direction(1, -1),
                new Direction(-1, -1),
                new Direction(1, 1),
                new Direction(-1, 1),
        });
    }

    @Override
    public boolean isWhiteKingRow(Coordinate coordinate) {
        return coordinate.getRow() == 0;
    }

    @Override
    public boolean isBlackKingRow(Coordinate coordinate) {
        return coordinate.getRow() == getBoardSize() - 1;
    }

    @Override
    public CellFill getWhiteCellFill() {
        return new EnglishCheckersCellFill(getBoardSize() - 3, getBoardSize() - 1);
    }

    @Override
    public CellFill getBlackCellFill() {
        return new EnglishCheckersCellFill(0, 2);
    }

}
