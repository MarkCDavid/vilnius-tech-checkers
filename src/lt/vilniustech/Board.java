package lt.vilniustech;

import lt.vilniustech.moves.JumpMove;
import lt.vilniustech.moves.Move;
import lt.vilniustech.moves.SimpleMove;
import lt.vilniustech.rulesets.CellFill;
import lt.vilniustech.rulesets.CheckersRuleset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

    public Board(CheckersRuleset ruleset) {
        this.ruleset = ruleset;
        CellFill blackCellFill = ruleset.getBlackCellFill();
        CellFill whiteCellFill = ruleset.getWhiteCellFill();
        this.cells = new Cell[ruleset.getBoardSize() * ruleset.getBoardSize()];
        for(int row = 0; row < ruleset.getBoardSize(); row++){
            for(int col = 0; col < ruleset.getBoardSize(); col++){
                Coordinate coordinate = new Coordinate(col, row);
                int index = coordinate.getIndex(ruleset.getBoardSize());
                this.cells[index] = new Cell();

                if(blackCellFill.fillCell(coordinate))
                    this.cells[index].setPiece(ruleset.createBlackPiece());

                if(whiteCellFill.fillCell(coordinate))
                    this.cells[index].setPiece(ruleset.createWhitePiece());
            }
        }
    }

    public List<Move> getAvailableMoves(Side side) {
        ArrayList<Move> availableMoves = new ArrayList<>();

        for(int i = 0; i < cells.length; i++) {
            Coordinate from = Coordinate.ofIndex(i, this.ruleset.getBoardSize());
            Piece piece = getCell(from).getPiece();
            if(piece != null && piece.getSide() == side)
                availableMoves.addAll(getAvailableMoves(from));
        }

        List<Move> jumpMoves = availableMoves.stream().filter(move -> move instanceof JumpMove).collect(Collectors.toList());

        if(jumpMoves.size() > 0)
            return jumpMoves;

        return availableMoves;
    }


    public List<Move> getAvailableMoves(Coordinate from) {
        ArrayList<Move> availableMoves = new ArrayList<>();

        Cell fromCell = getCell(from);
        if(fromCell == null) return availableMoves;

        Piece fromPiece = fromCell.getPiece();
        if(fromPiece == null) return availableMoves;

        Direction[] availableDirections = fromPiece.getDirections();
        for(Direction direction : availableDirections) {
            for(int moveSize = 1; moveSize <= fromPiece.getMoveSize(); moveSize++) {
                Move simple = new SimpleMove(from, direction, moveSize);
                if(simple.isValid(this)) {
                    availableMoves.add(simple);
                }
                else {
                    Move jump = new JumpMove(from, direction, moveSize + 1);
                    if(jump.isValid(this)){
                        availableMoves.add(jump);
                    }
                }
            }
        }

        List<Move> jumpMoves = availableMoves.stream().filter(move -> move instanceof JumpMove).collect(Collectors.toList());

        if(jumpMoves.size() > 0)
            return jumpMoves;

        return availableMoves;
    }

    public boolean doMove(Move move) {

        Side side = getCell(move.getFrom()).getPiece().getSide();
        boolean destinationIsKingRow = side == Side.BLACK ? ruleset.isBlackKingRow(move.getTo()) : ruleset.isWhiteKingRow(move.getTo());

        if(destinationIsKingRow) {
            move.perform(this);
            Piece kingPiece = side == Side.BLACK ? ruleset.createBlackKing() : ruleset.createWhiteKing();
            getCell(move.getTo()).setPiece(kingPiece);
            return false;
        }
        else {
            return move.perform(this);
        }
    }

    public void display() {
        for(int row = -1; row < this.ruleset.getBoardSize(); row++){
            if(row > -1) System.out.printf(" %d ", row);
            for(int col = 0; col < this.ruleset.getBoardSize(); col++){
                if(row == -1) {
                    if(col == 0) System.out.print(" XY");
                    System.out.printf(" %d ", col);
                }
                else {
                    int index = row * this.ruleset.getBoardSize() + col;
                    Piece piece = cells[index].getPiece();
                    char representation = piece == null ? ' ' : getRepresentation(piece.getSide());
                    System.out.printf("|%c|", representation);
                }
            }
            System.out.println();
        }
    }

    private char getRepresentation(Side side){
        switch (side) {
            case BLACK -> { return 'B'; }
            case WHITE -> { return 'W'; }
            default -> { return ' '; }
        }
    }

    public Cell getCell(Coordinate coordinate) {
        return validCoordinate(coordinate) ? cells[coordinate.getIndex(this.ruleset.getBoardSize())] : null;
    }

    private boolean validCoordinate(Coordinate coordinate) {
        boolean invalidX = coordinate.getColumn() < 0 || coordinate.getColumn() >= ruleset.getBoardSize();
        if(invalidX) return false;

        boolean invalidY = coordinate.getColumn() < 0 || coordinate.getColumn() >= ruleset.getBoardSize();
        if(invalidY) return false;

        int index = coordinate.getIndex(ruleset.getBoardSize());
        return index >= 0 && index < cells.length;
    }

    private final Cell[] cells;
    private final CheckersRuleset ruleset;
}
