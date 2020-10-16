//package lt.vilniustech.consolecheckers;
//
//import backend.Coordinate;
//import lt.vilniustech.Side;
//import backend.manager.CheckersManager;
//import backend.manager.GameManager;
//import backend.manager.backend.exceptions.IllegalCoordinateException;
//import backend.moves.Move;
//import backend.rulesets.international.InternationalCheckers;
//import backend.rulesets.turkish.TurkishCheckers;
//
//import javax.swing.text.html.Option;
//import java.util.Optional;
//import java.util.Scanner;
//
//public class ConsoleGame {
//
//
//    public static final String EXIT_GAME_OPTION = "exit";
//    public static final String SURRENDER_OPTION = "surrender";
//    public static final String DRAW_OPTION = "draw";
//
//    public static void main(String[] args) {
//        CheckersManager backend.manager = new GameManager(new InternationalCheckers());
//        ConsoleRenderer renderer = new ConsoleRenderer();
//        Scanner scanner = new Scanner(System.in);
//
//        while(true) {
//            renderer.render(backend.manager.getBoard());
//            System.out.printf("%s to move.%n", backend.manager.getCurrentSide());
//            System.out.printf("Available actions: %s, %s, %s.%n", DRAW_OPTION, SURRENDER_OPTION, EXIT_GAME_OPTION);
//            System.out.printf("Move format <from> <to>.%n");
//
//            String userInput = scanner.nextLine().toLowerCase();
//            switch (userInput) {
//                case DRAW_OPTION:
//                    backend.manager.setWinner(Side.NONE);
//                    return;
//                case SURRENDER_OPTION:
//                    backend.manager.setWinner(Side.opposite(backend.manager.getCurrentSide()));
//                    return;
//                case EXIT_GAME_OPTION:
//                    return;
//                default:
//                    String[] stringMove = userInput.split(" ");
//                    if (stringMove.length != 2) {
//                        System.out.printf("Incorrect amount of arguments. Type '<FROM CELL> <TO CELL>' to make a move.%n");
//                        continue;
//                    }
//
//                    backend.Coordinate from = parse(stringMove[0]);
//                    if(from == null)
//                        continue;
//
//                    backend.Coordinate to = parse(stringMove[1]);
//                    if(to == null)
//                        continue;
//
//                    Optional<Move> move = backend.manager.getAvailableMoves().stream().filter(m -> m.getFrom().equals(from) && m.getTo().equals(to)).findFirst();
//
//                    if(move.isEmpty())
//                        continue;
//
//                    backend.manager.processMove(move.get());
//                    break;
//            }
//
//            if(backend.manager.isFinished()) {
//                renderer.render(backend.manager.getBoard());
//                System.out.printf("Game finished! The winner is '%s'%n", backend.manager.getWinner());
//                break;
//            }
//        }
//    }
//
//    private static backend.Coordinate parse(String string) {
//        try {
//            return backend.Coordinate.ofString(string);
//        }
//        catch(IllegalCoordinateException exception) {
//            System.out.printf("backend.Coordinate %s is invalid!%n", string);
//            return null;
//        }
//    }
//
//
//}
