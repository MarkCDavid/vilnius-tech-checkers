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


    public boolean applyMove(Move move) {
        Piece piece = getCell(move.getFrom()).getPiece();
        Side side = piece.getSide();
        boolean destinationIsKingRow = ruleset.isKingRow(side, move.getTo());

        move.apply(this);
        if(destinationIsKingRow && !piece.isKing()) {
            Piece kingPiece = ruleset.createKing(side);
            getCell(move.getTo()).setPiece(kingPiece);
            return false;
        }
        return true;
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
