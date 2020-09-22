package lt.vilniustech;

import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.english.EnglishCheckers;

import java.util.List;
import java.util.Scanner;

public class Checkers {
    public static void main(String[] args) {
        Board board = new Board(new EnglishCheckers());
        Scanner scanner = new Scanner(System.in);

        Side side = Side.BLACK;
        Coordinate afterJump = null;

        while(true) {
            board.display();
            System.out.println();
            List<Move> moves;
            if(afterJump == null) moves = board.getAvailableMoves(side);
            else moves = board.getAvailableMoves(afterJump);
            for(int i = 0; i < moves.size(); i++) {
                System.out.printf("%d) %s%n", i, moves.get(i));
            }

            System.out.println("Type -1 to end the game!");
            int choice = scanner.nextInt();

            if(choice == -1) break;

            Move move = moves.get(choice);
            boolean anotherJump = board.doMove(move);
            if(!anotherJump) {
                side = Side.opposite(side);
                afterJump = null;
            }
            else
            {
                afterJump = move.getTo();
            }

        }
    }

}
