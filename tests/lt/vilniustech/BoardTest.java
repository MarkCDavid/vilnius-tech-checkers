//package lt.vilniustech;
//
//import lt.vilniustech.rulesets.CheckersRuleset;
//import lt.vilniustech.rulesets.international.InternationalCheckers;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class BoardTest {
//
//    @org.junit.jupiter.api.Test
//    void putPiece() {
//        Board board = new Board(new DummyRuleset().getBoardSize());
//        Piece piece = new Piece(Side.BLACK, new Direction[] {});
//        Coordinate coordinate = new Coordinate(4, 5);
//        board.putPiece(coordinate, piece);
//        assertEquals(board.getPieceMap().get(coordinate), piece);
//    }
//
//    @org.junit.jupiter.api.Test
//    void getPiece() {
//        Board board = new Board(new DummyRuleset().getBoardSize()s);
//        Piece piece = new Piece(Side.BLACK, new Direction[] {});
//        Coordinate coordinate = new Coordinate(4, 5);
//        board.getPieceMap().put(coordinate, piece);
//        assertEquals(board.getPiece(coordinate), piece);
//    }
//
//    @org.junit.jupiter.api.Test
//    void popPiece() {
//        Board board = new Board(new DummyRuleset());
//        Piece piece = new Piece(Side.BLACK, new Direction[] {});
//        Coordinate coordinate = new Coordinate(4, 5);
//        board.putPiece(coordinate, piece);
//        assertEquals(board.getPiece(coordinate), piece);
//        assertEquals(board.popPiece(coordinate), piece);
//        assertNull(board.getPiece(coordinate));
//    }
//
//    @org.junit.jupiter.api.Test
//    void swapPieces() {
//        Board board = new Board(new DummyRuleset());
//        Piece piece1 = new Piece(Side.BLACK, new Direction[] {});
//        Coordinate coordinate1 = new Coordinate(4, 5);
//        Piece piece2 = new Piece(Side.BLACK, new Direction[] {});
//        Coordinate coordinate2 = new Coordinate(5, 6);
//        board.putPiece(coordinate1, piece1);
//        board.putPiece(coordinate2, piece2);
//        board.swapPieces(coordinate1, coordinate2);
//        assertEquals(board.getPiece(coordinate1), piece2);
//        assertEquals(board.getPiece(coordinate2), piece1);
//    }
//
//    @org.junit.jupiter.api.Test
//    void validCoordinate() {
//        CheckersRuleset ruleset = new DummyRuleset();
//        Board board = new Board(ruleset);
//        assertTrue(board.validCoordinate(new Coordinate(5, 6)));
//        assertTrue(board.validCoordinate(new Coordinate(0, 0)));
//        assertTrue(board.validCoordinate(new Coordinate(ruleset.getBoardSize() - 1, ruleset.getBoardSize() - 1)));
//        assertFalse(board.validCoordinate(new Coordinate(ruleset.getBoardSize(), 0)));
//
//        assertTrue(board.validCoordinate(Coordinate.ofIndex(0, ruleset.getBoardSize())));
//        assertTrue(board.validCoordinate(Coordinate.ofIndex(ruleset.getBoardSize() * ruleset.getBoardSize() - 1, ruleset.getBoardSize())));
//        assertFalse(board.validCoordinate(Coordinate.ofIndex(ruleset.getBoardSize() * ruleset.getBoardSize(), ruleset.getBoardSize())));
//        assertFalse(board.validCoordinate(Coordinate.ofIndex(-1, ruleset.getBoardSize())));
//    }
//}