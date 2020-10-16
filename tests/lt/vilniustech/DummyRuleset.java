//package lt.vilniustech;
//
//import backend.moves.Move;
//import backend.rulesets.CaptureConstraints;
//import backend.side.CoordinateValidator;
//import backend.rulesets.CheckersRuleset;
//
//import java.util.List;
//
//public class DummyRuleset implements CheckersRuleset {
//
//    @Override
//    public String toString() {
//        return "Dummy Checkers";
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
//        return Side.NONE;
//    }
//
//    @Override
//    public Side getFirstToMove() {
//        return Side.BLACK;
//    }
//
//    @Override
//    public CaptureConstraints getCaptureConstraints(backend.Board board, Move move) {
//        return new CaptureConstraints() {
//            @Override
//            public List<Move> filterMoves(List<Move> availableMoves) {
//                return availableMoves;
//            }
//
//            @Override
//            public Side getNextSide(Side backend.side) {
//                return Side.opposite(backend.side);
//            }
//
//            @Override
//            public void setMultipleCaptures(boolean multipleCaptures) {
//
//            }
//        };
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
//    public CoordinateValidator getCellFill(Side backend.side) {
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
//        return new backend.Piece(Side.WHITE, new backend.Direction[]{});
//    }
//
//    @Override
//    public backend.Piece createBlackPiece() {
//        return new backend.Piece(Side.BLACK, new backend.Direction[]{});
//    }
//
//    @Override
//    public backend.Piece createWhiteKing() {
//        return new backend.Piece(Side.WHITE, new backend.Direction[]{}, true);
//    }
//    @Override
//    public backend.Piece createBlackKing() {
//        return new backend.Piece(Side.BLACK, new backend.Direction[]{}, true);
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
//    public CoordinateValidator getWhiteCellFill() { return whiteCellFill; }
//
//    @Override
//    public CoordinateValidator getBlackCellFill() { return blackCellFill; }
//
//
//    private final CoordinateValidator whiteCellFill = new CoordinateValidator() {
//        @Override
//        protected boolean rowValid(backend.Coordinate coordinate) {
//            return false;
//        }
//
//        @Override
//        protected boolean columnValid(backend.Coordinate coordinate) {
//            return false;
//        }
//    };
//    private final CoordinateValidator blackCellFill = new CoordinateValidator() {
//        @Override
//        protected boolean rowValid(backend.Coordinate coordinate) {
//            return false;
//        }
//
//        @Override
//        protected boolean columnValid(backend.Coordinate coordinate) {
//            return false;
//        }
//    };
//}
