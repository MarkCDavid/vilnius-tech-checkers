package lt.vilniustech;

import lt.vilniustech.consolecheckers.ConsoleRenderer;
import lt.vilniustech.rulesets.english.EnglishCheckers;

import java.util.Objects;
import java.util.Scanner;

public class Checkers {

    public static final String EXIT_GAME_OPTION = "exit";

    public static void main(String[] args) {

        GameManager manager = new GameManager(new Board(new EnglishCheckers()));
        Renderer renderer = new ConsoleRenderer();
        Scanner scanner = new Scanner(System.in);


        while(true) {
            renderer.render(manager.getBoard());
            System.out.println(manager.getCurrentSide());

            System.out.printf("Type '%s' to finish the game.%n", EXIT_GAME_OPTION);
            System.out.printf("Type '<FROM CELL> <TO CELL>' to make a move.%n");

            String choice = scanner.nextLine();

            if(Objects.equals(EXIT_GAME_OPTION, choice.toLowerCase()))
                break;

            String[] action = choice.split(" ");
            if(action.length != 2) {
                System.out.printf("Incorrect amount of arguments. Type '<FROM CELL> <TO CELL>' to make a move.%n");
                continue;
            }

            manager.processInput(action[0], action[1], exception -> {
                System.out.println(exception.getMessage());
            });

            if(manager.isFinished()) {
                renderer.render(manager.getBoard());
                System.out.printf("Game finished! The winner is '%s'%n", manager.getWinner());
                break;
            }
        }
    }

}
