package lt.vilniustech;

public class JumpMove implements Move {

    public JumpMove(Coordinate from, Coordinate over, Coordinate to) {
        this.from = from;
        this.over = over;
        this.to = to;
    }

    public JumpMove(Coordinate from, Direction direction, int moveSize) {
        this.from = from;
        this.to = from.move(direction, moveSize);
        this.over = from.move(direction, moveSize - 1);
    }

    @Override
    public Coordinate getFrom() {
        return from;
    }

    @Override
    public Coordinate getTo() {
        return to;
    }

    public Coordinate getOver() {
        return over;
    }

    @Override
    public boolean isValid(Board board) {
        Cell fromCell = board.getCell(this.from);
        Cell overCell = board.getCell(this.over);
        Cell toCell = board.getCell(this.to);
        if(fromCell == null || toCell == null || overCell == null) return false;

        Piece fromPiece = fromCell.getPiece();
        Piece overPiece = overCell.getPiece();
        Piece toPiece = toCell.getPiece();
        return fromPiece != null && overPiece != null && toPiece == null;
    }

    @Override
    public void perform(Board board) {
        Cell from = board.getCell(this.from);
        Cell over = board.getCell(this.over);
        Cell to = board.getCell(this.to);

        if(from == null || over == null || to == null) return;
        if(over.getPiece() == null) return;

        over.popPiece();
        to.setPiece(from.popPiece());
    }

    @Override
    public String toString() {
        return String.format("%s --%s-> %s", from, over, to);
    }

    private final Coordinate from;
    private final Coordinate over;
    private final Coordinate to;
}
