package lt.vilniustech;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public Board(int size, int filledRows) {
        this.size = size;
        this.cells = new Cell[size * size];
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                int index = row * size + col;
                int offset = (row + 1) % 2;
                this.cells[index] = new Cell();

                if(row < filledRows) {
                    if((col + offset) % 2 == 0)
                        this.cells[index].setPiece(new Piece('B', 1));
                }

                if(row >= size - filledRows){
                    if((col + offset) % 2 == 0)
                        this.cells[index].setPiece(new Piece('W', 1));
                }
            }
        }
    }

    public List<Move> getAvailableMoves(char side) {
        ArrayList<Move> availableMoves = new ArrayList<>();

        for(int i = 0; i < cells.length; i++) {
            Coordinate from = Coordinate.ofIndex(i, this.size);
            Piece piece = getCell(from).getPiece();
            if(piece != null && piece.getSide() == side)
                availableMoves.addAll(getAvailableMoves(from));
        }
        return availableMoves;
    }


    public List<Move> getAvailableMoves(Coordinate from) {
        ArrayList<Move> availableMoves = new ArrayList<>();

        Cell fromCell = getCell(from);
        if(fromCell == null) return availableMoves;

        Piece fromPiece = fromCell.getPiece();
        if(fromPiece == null) return availableMoves;

        Direction[] availableDirections = fromPiece.getAvailableDirections();
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

        return availableMoves;
    }

    public void doMove(Move move) {
        move.perform(this);
    }

    public void display() {
        for(int row = 0; row < this.size; row++){
            for(int col = 0; col < this.size; col++){
                int index = row * this.size + col;
                Piece piece = cells[index].getPiece();
                char representation = piece != null ? piece.getSide() : ' ';
                System.out.printf("|%c|", representation);
            }
            System.out.println();
        }
    }

    public Cell getCell(Coordinate coordinate) {
        return validCoordinate(coordinate) ? cells[coordinate.getIndex(this.size)] : null;
    }

    private boolean validCoordinate(Coordinate coordinate) {
        boolean invalidX = coordinate.getX() < 0 || coordinate.getX() >= size;
        if(invalidX) return false;

        boolean invalidY = coordinate.getX() < 0 || coordinate.getX() >= size;
        if(invalidY) return false;

        int index = coordinate.getIndex(size);
        return index >= 0 && index < cells.length;
    }

    private final int size;
    private final Cell[] cells;
}
