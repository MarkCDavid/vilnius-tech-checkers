package lt.vilniustech;

import lt.vilniustech.moves.*;
import lt.vilniustech.rulesets.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board implements Iterable<Cell> {

    public CheckersRuleset getRuleset() { return ruleset; }

    public Board(CheckersRuleset ruleset) {
        this.ruleset = ruleset;
        this.cells = new Cell[ruleset.getBoardSize() * ruleset.getBoardSize()];

        for(int row = 0; row < ruleset.getBoardSize(); row++){
            for(int column = 0; column < ruleset.getBoardSize(); column++){

                Coordinate coordinate = new Coordinate(column, row);
                Cell cell = setCell(coordinate, new Cell());

                if(ruleset.getCellFill(Side.BLACK).fillCell(coordinate))
                    cell.setPiece(ruleset.createPiece(Side.BLACK));

                if(ruleset.getCellFill(Side.WHITE).fillCell(coordinate))
                    cell.setPiece(ruleset.createPiece(Side.WHITE));
            }
        }
    }

    public List<Move> getAvailableMoves(Side side) {

        ArrayList<Move> availableMoves = new ArrayList<>();

        for(int i = 0; i < cells.length; i++) {
            Coordinate from = Coordinate.ofIndex(i, ruleset.getBoardSize());
            availableMoves.addAll(getAvailableMoves(side, from));
        }

        return availableMoves;
    }


    public List<Move> getAvailableMoves(Side side, Coordinate from) {
        ArrayList<Move> availableMoves = new ArrayList<>();

        Cell fromCell = getCell(from);
        if(fromCell == null) return availableMoves;

        Piece fromPiece = fromCell.getPiece();
        if(fromPiece == null || fromPiece.getSide() != side) return availableMoves;

        for(Direction direction : fromPiece.getDirections()) {
            for(int moveSize = 1; moveSize <= fromPiece.getMoveSize(); moveSize++) {
                Move move = getMove(from, direction, moveSize);
                if(move == null) break;
                availableMoves.add(move);
                if(move instanceof CaptureMove) break;

            }
        }

        return availableMoves;
    }

    private Move getMove(Coordinate from, Direction direction, int moveSize) {
        Move simple = new SimpleMove(from, direction, moveSize);
        if(simple.isValid(this)) return simple;
        else {
            Move jump = new CaptureMove(from, direction, moveSize + 1);
            if(jump.isValid(this)) return jump;
            return null;
        }
    }

    public boolean applyMove(Move move) {
        Side side = getCell(move.getFrom()).getPiece().getSide();
        boolean destinationIsKingRow = ruleset.isKingRow(side, move.getTo());

        if(destinationIsKingRow) {
            move.apply(this);
            Piece kingPiece = ruleset.createKing(side);
            getCell(move.getTo()).setPiece(kingPiece);
            return false;
        }
        else {
            return move.apply(this);
        }
    }

    public Cell setCell(Coordinate coordinate, Cell cell) {
        if(!validCoordinate(coordinate)) return null;
        cells[coordinate.getIndex(ruleset.getBoardSize())] = cell;
        return cell;
    }

    public Cell getCell(Coordinate coordinate) {
        return validCoordinate(coordinate) ? cells[coordinate.getIndex(ruleset.getBoardSize())] : null;
    }

    public List<Piece> getSidePieces(Side side) {
        ArrayList<Piece> pieces = new ArrayList<>();
        for(Cell cell: this) {
            Piece piece = cell.getPiece();
            if(piece != null && piece.getSide() == side)
                pieces.add(piece);
        }
        return pieces;
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


    @Override
    public Iterator<Cell> iterator() {
        return new BoardIterator(this);
    }
}
