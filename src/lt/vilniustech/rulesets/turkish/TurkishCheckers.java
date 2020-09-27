package lt.vilniustech.rulesets.turkish;

import lt.vilniustech.*;
import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.CaptureConstraints;
import lt.vilniustech.rulesets.CellFill;
import lt.vilniustech.rulesets.CheckersRuleset;

import java.util.List;

public class TurkishCheckers implements CheckersRuleset {

    @Override
    public String toString() {
        return "Turkish Checkers";
    }

    @Override
    public int getBoardSize() {
        return 8;
    }

    @Override
    public Side processWinningConditions(List<Move> moves, List<Piece> whitePieces, List<Piece> blackPieces) {
        if(whitePieces.size() == 0) return Side.BLACK;
        else if(blackPieces.size() == 0) return Side.WHITE;
        else if(whitePieces.size() == 1 && blackPieces.size() == 1) {
            if(whitePieces.get(0).isKing() && !blackPieces.get(0).isKing()) return Side.WHITE;
            else if(!whitePieces.get(0).isKing() && blackPieces.get(0).isKing()) return Side.BLACK;
        }
        else if(moves.size() == 0) return Side.DRAW;
        return Side.NONE;
    }

    @Override
    public Side getFirstToMove() {
        return Side.WHITE;
    }

    @Override
    public CaptureConstraints getCaptureConstraints(Board board, Move move) {
        return new TurkishCheckersCaptureConstraints(board, move);
    }

    @Override
    public boolean isKingRow(Side side, Coordinate coordinate) {
        switch (side) {
            case BLACK -> { return isBlackKingRow(coordinate); }
            case WHITE -> { return isWhiteKingRow(coordinate); }
            default -> throw new IllegalStateException("Unexpected value: " + side);
        }
    }

    @Override
    public CellFill getCellFill(Side side) {
        switch (side) {
            case BLACK -> { return getBlackCellFill(); }
            case WHITE -> { return getWhiteCellFill(); }
            default -> throw new IllegalStateException("Unexpected value: " + side);
        }
    }

    @Override
    public Piece createPiece(Side side) {
        switch (side) {
            case BLACK -> { return createBlackPiece(); }
            case WHITE -> { return createWhitePiece(); }
            default -> throw new IllegalStateException("Unexpected value: " + side);
        }
    }

    @Override
    public Piece createKing(Side side) {
        switch (side) {
            case BLACK -> { return createBlackKing(); }
            case WHITE -> { return createWhiteKing(); }
            default -> throw new IllegalStateException("Unexpected value: " + side);
        }
    }

    @Override
    public Piece createWhitePiece() {
        return new Piece(Side.WHITE, new Direction[]{
                new Direction(1, 0),
                new Direction(-1, 0),
                new Direction(0, -1),
        });
    }

    @Override
    public Piece createBlackPiece() {
        return new Piece(Side.BLACK, new Direction[]{
                new Direction(1, 0),
                new Direction(-1, 0),
                new Direction(0, 1),
        });
    }

    @Override
    public Piece createWhiteKing() {
        return new Piece(Side.WHITE, new Direction[]{
                new Direction(1, 0),
                new Direction(-1, 0),
                new Direction(0, 1),
                new Direction(0, -1),
        }, getBoardSize(), true);
    }
    @Override
    public Piece createBlackKing() {
        return new Piece(Side.BLACK, new Direction[]{
                new Direction(1, 0),
                new Direction(-1, 0),
                new Direction(0, 1),
                new Direction(0, -1),
        }, getBoardSize(), true);
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
    public CellFill getWhiteCellFill() { return whiteCellFill; }

    @Override
    public CellFill getBlackCellFill() { return blackCellFill; }


    private final CellFill whiteCellFill = new TurkishCheckersCellFill(getBoardSize() - 3, getBoardSize() - 2);
    private final CellFill blackCellFill = new TurkishCheckersCellFill(1, 2);
}
