package lt.vilniustech;

public class EnglishCheckers implements CheckersRuleset {

    @Override
    public int getBoardSize() {
        return 8;
    }

    @Override
    public int getFilledRowCount() {
        return 3;
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
}
