package lt.vilniustech;

public class Checkers {
    public static void main(String[] args) {
        Board board = new Board(8, 3);
        board.display();

        System.out.println();
        for(Move move: board.getAvailableMoves(new Coordinate(2, 5))) {
            System.out.println(move);
        }
    }
}
