//package lt.vilniustech;
//
//import backend.rulesets.CheckersRuleset;
//import backend.rulesets.international.InternationalCheckers;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class BoardTest {
//
//    @org.junit.jupiter.api.Test
//    void putPiece() {
//        backend.Board board = new backend.Board(new DummyRuleset().getBoardSize());
//        backend.Piece piece = new backend.Piece(Side.BLACK, new backend.Direction[] {});
//        backend.Coordinate coordinate = new backend.Coordinate(4, 5);
//        board.putPiece(coordinate, piece);
//        assertEquals(board.getPieceMap().get(coordinate), piece);
//    }
//
//    @org.junit.jupiter.api.Test
//    void getPiece() {
//        backend.Board board = new backend.Board(new DummyRuleset().getBoardSize()s);
//        backend.Piece piece = new backend.Piece(Side.BLACK, new backend.Direction[] {});
//        backend.Coordinate coordinate = new backend.Coordinate(4, 5);
//        board.getPieceMap().put(coordinate, piece);
//        assertEquals(board.getPiece(coordinate), piece);
//    }
//
//    @org.junit.jupiter.api.Test
//    void popPiece() {
//        backend.Board board = new backend.Board(new DummyRuleset());
//        backend.Piece piece = new backend.Piece(Side.BLACK, new backend.Direction[] {});
//        backend.Coordinate coordinate = new backend.Coordinate(4, 5);
//        board.putPiece(coordinate, piece);
//        assertEquals(board.getPiece(coordinate), piece);
//        assertEquals(board.popPiece(coordinate), piece);
//        assertNull(board.getPiece(coordinate));
//    }
//
//    @org.junit.jupiter.api.Test
//    void swapPieces() {
//        backend.Board board = new backend.Board(new DummyRuleset());
//        backend.Piece piece1 = new backend.Piece(Side.BLACK, new backend.Direction[] {});
//        backend.Coordinate coordinate1 = new backend.Coordinate(4, 5);
//        backend.Piece piece2 = new backend.Piece(Side.BLACK, new backend.Direction[] {});
//        backend.Coordinate coordinate2 = new backend.Coordinate(5, 6);
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
//        backend.Board board = new backend.Board(ruleset);
//        assertTrue(board.validCoordinate(new backend.Coordinate(5, 6)));
//        assertTrue(board.validCoordinate(new backend.Coordinate(0, 0)));
//        assertTrue(board.validCoordinate(new backend.Coordinate(ruleset.getBoardSize() - 1, ruleset.getBoardSize() - 1)));
//        assertFalse(board.validCoordinate(new backend.Coordinate(ruleset.getBoardSize(), 0)));
//
//        assertTrue(board.validCoordinate(backend.Coordinate.ofIndex(0, ruleset.getBoardSize())));
//        assertTrue(board.validCoordinate(backend.Coordinate.ofIndex(ruleset.getBoardSize() * ruleset.getBoardSize() - 1, ruleset.getBoardSize())));
//        assertFalse(board.validCoordinate(backend.Coordinate.ofIndex(ruleset.getBoardSize() * ruleset.getBoardSize(), ruleset.getBoardSize())));
//        assertFalse(board.validCoordinate(backend.Coordinate.ofIndex(-1, ruleset.getBoardSize())));
//    }
//}