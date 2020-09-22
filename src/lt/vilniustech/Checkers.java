package lt.vilniustech;

import java.util.List;
import java.util.Scanner;

public class Checkers {
    public static void main(String[] args) {
        Board board = new Board(new EnglishCheckers());
        Scanner scanner = new Scanner(System.in);

        Side side = Side.BLACK;

        while(true) {
            board.display();
            System.out.println();
            List<Move> moves = board.getAvailableMoves(side);
            for(int i = 0; i < moves.size(); i++) {
                System.out.printf("%d) %s%n", i, moves.get(i));
            }

            System.out.println("Type -1 to end the game!");
            int choice = scanner.nextInt();

            if(choice == -1) break;

            board.doMove(moves.get(choice));

            side = Side.opposite(side);
        }
    }

}
