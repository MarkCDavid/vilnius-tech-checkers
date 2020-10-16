//package backend.rulesets.italian;
//
//import lt.vilniustech.*;
//import backend.moves.Move;
//import backend.rulesets.CaptureConstraints;
//import backend.side.PieceSetter;
//import backend.rulesets.CheckersRuleset;
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
//    public boolean canJumpAnywhereBeyond(backend.Piece piece) {
//        return false;
//    }
//
//    @Override
//    public Side processWinningConditions(Side currentSide, List<Move> backend.moves, List<backend.Piece> whitePieces, List<backend.Piece> blackPieces) {
//        if(whitePieces.size() == 0) return Side.BLACK;
//        else if(blackPieces.size() == 0) return Side.WHITE;
//        else if(backend.moves.size() == 0) return Side.DRAW;
//        return Side.NONE;
//    }
//
//    @Override
//    public Side getFirstToMove() {
//        return Side.WHITE;
//    }
//
//    @Override
//    public CaptureConstraints getCaptureConstraints(backend.Board board, Move move) {
//        return new ItalianCheckersCaptureConstraints(board, move);
//    }
//
//    @Override
//    public boolean isKingRow(Side backend.side, backend.Coordinate coordinate) {
//        switch (backend.side) {
//            case BLACK -> { return isBlackKingRow(coordinate); }
//            case WHITE -> { return isWhiteKingRow(coordinate); }
//            default -> throw new IllegalStateException("Unexpected value: " + backend.side);
//        }
//    }
//
//    @Override
//    public PieceSetter getCellFill(Side backend.side) {
//        switch (backend.side) {
//            case BLACK -> { return getBlackCellFill(); }
//            case WHITE -> { return getWhiteCellFill(); }
//            default -> throw new IllegalStateException("Unexpected value: " + backend.side);
//        }
//    }
//
//    @Override
//    public backend.Piece createPiece(Side backend.side) {
//        switch (backend.side) {
//            case BLACK -> { return createBlackPiece(); }
//            case WHITE -> { return createWhitePiece(); }
//            default -> throw new IllegalStateException("Unexpected value: " + backend.side);
//        }
//    }
//
//    @Override
//    public backend.Piece createKing(Side backend.side) {
//        switch (backend.side) {
//            case BLACK -> { return createBlackKing(); }
//            case WHITE -> { return createWhiteKing(); }
//            default -> throw new IllegalStateException("Unexpected value: " + backend.side);
//        }
//    }
//
//    @Override
//    public backend.Piece createWhitePiece() {
//        return new backend.Piece(Side.WHITE, new backend.Direction[]{
//                new backend.Direction(1, -1),
//                new backend.Direction(-1, -1),
//        });
//    }
//
//    @Override
//    public backend.Piece createBlackPiece() {
//        return new backend.Piece(Side.BLACK, new backend.Direction[]{
//                new backend.Direction(1, 1),
//                new backend.Direction(-1, 1),
//        });
//    }
//
//    @Override
//    public backend.Piece createWhiteKing() {
//        return new backend.Piece(Side.WHITE, new backend.Direction[]{
//                new backend.Direction(1, -1),
//                new backend.Direction(-1, -1),
//                new backend.Direction(1, 1),
//                new backend.Direction(-1, 1),
//        }, true);
//    }
//    @Override
//    public backend.Piece createBlackKing() {
//        return new backend.Piece(Side.BLACK, new backend.Direction[]{
//                new backend.Direction(1, -1),
//                new backend.Direction(-1, -1),
//                new backend.Direction(1, 1),
//                new backend.Direction(-1, 1),
//        }, true);
//    }
//
//    @Override
//    public boolean isWhiteKingRow(backend.Coordinate coordinate) {
//        return coordinate.getRow() == 0;
//    }
//
//    @Override
//    public boolean isBlackKingRow(backend.Coordinate coordinate) {
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
