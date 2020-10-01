package lt.vilniustech.consolecheckers;

import lt.vilniustech.Coordinate;
import lt.vilniustech.Side;
import lt.vilniustech.manager.CheckersManager;
import lt.vilniustech.manager.GameManager;
import lt.vilniustech.manager.IllegalCoordinateException;
import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.international.InternationalCheckers;
import lt.vilniustech.rulesets.turkish.TurkishCheckers;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleGame {


    public static final String EXIT_GAME_OPTION = "exit";
    public static final String SURRENDER_OPTION = "surrender";
    public static final String DRAW_OPTION = "draw";

    public static void main(String[] args) {
        CheckersManager manager = new GameManager(new InternationalCheckers());
        ConsoleRenderer renderer = new ConsoleRenderer();
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
                    return;
                case SURRENDER_OPTION:
                    manager.setWinner(Side.opposite(manager.getCurrentSide()));
                    return;
                case EXIT_GAME_OPTION:
                    return;
                default:
                    String[] stringMove = userInput.split(" ");
                    if (stringMove.length != 2) {
                        System.out.printf("Incorrect amount of arguments. Type '<FROM CELL> <TO CELL>' to make a move.%n");
                        continue;
                    }

                    Coordinate from = parse(stringMove[0]);
                    if(from == null)
                        continue;

                    Coordinate to = parse(stringMove[1]);
                    if(to == null)
                        continue;

                    Optional<Move> move = manager.getAvailableMoves().stream().filter(m -> m.getFrom().equals(from) && m.getTo().equals(to)).findFirst();

                    if(move.isEmpty())
                        continue;

                    manager.processMove(move.get());
                    break;
            }

            if(manager.isFinished()) {
                renderer.render(manager.getBoard());
                System.out.printf("Game finished! The winner is '%s'%n", manager.getWinner());
                break;
            }
        }
    }

    private static Coordinate parse(String string) {
        try {
            return Coordinate.ofString(string);
        }
        catch(IllegalCoordinateException exception) {
            System.out.printf("Coordinate %s is invalid!%n", string);
            return null;
        }
    }


}
