package lt.vilniustech.consolecheckers;

import lt.vilniustech.Renderer;
import lt.vilniustech.Side;
import lt.vilniustech.manager.GameManager;
import lt.vilniustech.rulesets.turkish.TurkishCheckers;

import java.util.Scanner;

public class ConsoleGame {


    public static final String EXIT_GAME_OPTION = "exit";
    public static final String SURRENDER_OPTION = "surrender";
    public static final String DRAW_OPTION = "draw";

    public static void main(String[] args) {
        GameManager manager = new GameManager(new TurkishCheckers());
        Renderer renderer = new ConsoleRenderer();
        Scanner scanner = new Scanner(System.in);

        while(true) {
            renderer.render(manager.getBoard());
            System.out.printf("%s to move.%n", manager.getCurrentSide());
            System.out.printf("Available actions: %s, %s, %s.%n", DRAW_OPTION, SURRENDER_OPTION, EXIT_GAME_OPTION);
            System.out.printf("Move format <from> <to>.%n");

            String userInput = scanner.nextLine().toLowerCase();
            switch (userInput) {
                case DRAW_OPTION:
                    manager.setWinner(Side.NONE);
                    break;
                case SURRENDER_OPTION:
                    manager.setWinner(Side.opposite(manager.getCurrentSide()));
                    break;
                case EXIT_GAME_OPTION:
                    return;
                default:
                    String[] move = userInput.split(" ");
                    if (move.length != 2) {
                        System.out.printf("Incorrect amount of arguments. Type '<FROM CELL> <TO CELL>' to make a move.%n");
                        continue;
                    }
                    manager.processInput(move[0], move[1], exception -> {
                        System.out.println(exception.getMessage());
                    });
                    break;
            }

            if(manager.isFinished()) {
                renderer.render(manager.getBoard());
                System.out.printf("Game finished! The winner is '%s'%n", manager.getWinner());
                break;
            }
        }

    }


}
