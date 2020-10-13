//package lt.vilniustech;
//
//import lt.vilniustech.moves.Move;
//import lt.vilniustech.rulesets.CaptureConstraints;
//import lt.vilniustech.side.CoordinateValidator;
//import lt.vilniustech.rulesets.CheckersRuleset;
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
//    public boolean canJumpAnywhereBeyond(Piece piece) {
//        return false;
//    }
//
//    @Override
//    public Side processWinningConditions(Side currentSide, List<Move> moves, List<Piece> whitePieces, List<Piece> blackPieces) {
//        return Side.NONE;
//    }
//
//    @Override
//    public Side getFirstToMove() {
//        return Side.BLACK;
//    }
//
//    @Override
//    public CaptureConstraints getCaptureConstraints(Board board, Move move) {
//        return new CaptureConstraints() {
//            @Override
//            public List<Move> filterMoves(List<Move> availableMoves) {
//                return availableMoves;
//            }
//
//            @Override
//            public Side getNextSide(Side side) {
//                return Side.opposite(side);
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
//    public boolean isKingRow(Side side, Coordinate coordinate) {
//        switch (side) {
//            case BLACK -> { return isBlackKingRow(coordinate); }
//            case WHITE -> { return isWhiteKingRow(coordinate); }
//            default -> throw new IllegalStateException("Unexpected value: " + side);
//        }
//    }
//
//    @Override
//    public CoordinateValidator getCellFill(Side side) {
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
//        return new Piece(Side.WHITE, new Direction[]{});
//    }
//
//    @Override
//    public Piece createBlackPiece() {
//        return new Piece(Side.BLACK, new Direction[]{});
//    }
//
//    @Override
//    public Piece createWhiteKing() {
//        return new Piece(Side.WHITE, new Direction[]{}, true);
//    }
//    @Override
//    public Piece createBlackKing() {
//        return new Piece(Side.BLACK, new Direction[]{}, true);
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
//    public CoordinateValidator getWhiteCellFill() { return whiteCellFill; }
//
//    @Override
//    public CoordinateValidator getBlackCellFill() { return blackCellFill; }
//
//
//    private final CoordinateValidator whiteCellFill = new CoordinateValidator() {
//        @Override
//        protected boolean rowValid(Coordinate coordinate) {
//            return false;
//        }
//
//        @Override
//        protected boolean columnValid(Coordinate coordinate) {
//            return false;
//        }
//    };
//    private final CoordinateValidator blackCellFill = new CoordinateValidator() {
//        @Override
//        protected boolean rowValid(Coordinate coordinate) {
//            return false;
//        }
//
//        @Override
//        protected boolean columnValid(Coordinate coordinate) {
//            return false;
//        }
//    };
//}
