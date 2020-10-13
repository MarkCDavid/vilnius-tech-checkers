//package lt.vilniustech.rulesets.italian;
//
//import lt.vilniustech.*;
//import lt.vilniustech.moves.Move;
//import lt.vilniustech.rulesets.CaptureConstraints;
//import lt.vilniustech.side.PieceSetter;
//import lt.vilniustech.rulesets.CheckersRuleset;
//
//import java.util.List;
//
//public class ItalianCheckers implements CheckersRuleset {
//
//    @Override
//    public String toString() {
//        return "Italian Checkers";
//    }
//
//    @Override
//    public int getBoardSize() {
//        return 8;
//    }
//
//    @Override
//    public boolean isCaptureImmediate() {
//        return false;
//    }
//
//    @Override
//    public boolean isPromotionImmediate() {
//        return true;
//    }
//
//    @Override
//    public boolean canJumpAnywhereBeyond(Piece piece) {
//        return false;
//    }
//
//    @Override
//    public Side processWinningConditions(Side currentSide, List<Move> moves, List<Piece> whitePieces, List<Piece> blackPieces) {
//        if(whitePieces.size() == 0) return Side.BLACK;
//        else if(blackPieces.size() == 0) return Side.WHITE;
//        else if(moves.size() == 0) return Side.DRAW;
//        return Side.NONE;
//    }
//
//    @Override
//    public Side getFirstToMove() {
//        return Side.WHITE;
//    }
//
//    @Override
//    public CaptureConstraints getCaptureConstraints(Board board, Move move) {
//        return new ItalianCheckersCaptureConstraints(board, move);
//    }
//
//    @Override
//    public boolean isKingRow(Side side, Coordinate coordinate) {
//        switch (side) {
//            case BLACK -> { return isBlackKingRow(coordinate); }
//            case WHITE -> { return isWhiteKingRow(coordinate); }
//            default -> throw new IllegalStateException("Unexpected value: " + side);
//        }
//    }
//
//    @Override
//    public PieceSetter getCellFill(Side side) {
//        switch (side) {
//            case BLACK -> { return getBlackCellFill(); }
//            case WHITE -> { return getWhiteCellFill(); }
//            default -> throw new IllegalStateException("Unexpected value: " + side);
//        }
//    }
//
//    @Override
//    public Piece createPiece(Side side) {
//        switch (side) {
//            case BLACK -> { return createBlackPiece(); }
//            case WHITE -> { return createWhitePiece(); }
//            default -> throw new IllegalStateException("Unexpected value: " + side);
//        }
//    }
//
//    @Override
//    public Piece createKing(Side side) {
//        switch (side) {
//            case BLACK -> { return createBlackKing(); }
//            case WHITE -> { return createWhiteKing(); }
//            default -> throw new IllegalStateException("Unexpected value: " + side);
//        }
//    }
//
//    @Override
//    public Piece createWhitePiece() {
//        return new Piece(Side.WHITE, new Direction[]{
//                new Direction(1, -1),
//                new Direction(-1, -1),
//        });
//    }
//
//    @Override
//    public Piece createBlackPiece() {
//        return new Piece(Side.BLACK, new Direction[]{
//                new Direction(1, 1),
//                new Direction(-1, 1),
//        });
//    }
//
//    @Override
//    public Piece createWhiteKing() {
//        return new Piece(Side.WHITE, new Direction[]{
//                new Direction(1, -1),
//                new Direction(-1, -1),
//                new Direction(1, 1),
//                new Direction(-1, 1),
//        }, true);
//    }
//    @Override
//    public Piece createBlackKing() {
//        return new Piece(Side.BLACK, new Direction[]{
//                new Direction(1, -1),
//                new Direction(-1, -1),
//                new Direction(1, 1),
//                new Direction(-1, 1),
//        }, true);
//    }
//
//    @Override
//    public boolean isWhiteKingRow(Coordinate coordinate) {
//        return coordinate.getRow() == 0;
//    }
//
//    @Override
//    public boolean isBlackKingRow(Coordinate coordinate) {
//        return coordinate.getRow() == getBoardSize() - 1;
//    }
//
//    @Override
//    public PieceSetter getWhiteCellFill() { return whiteCellFill; }
//
//    @Override
//    public PieceSetter getBlackCellFill() { return blackCellFill; }
//
//
//    private final PieceSetter whiteCellFill = new ItalianCheckersCellFill(getBoardSize() - 3, getBoardSize() - 1);
//    private final PieceSetter blackCellFill = new ItalianCheckersCellFill(0, 2);
//}
