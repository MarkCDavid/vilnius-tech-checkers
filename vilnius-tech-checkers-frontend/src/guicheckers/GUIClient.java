package guicheckers;

import api.services.GameService;
import api.services.mock.*;
import guicheckers.controller.MainMenuController;
import guicheckers.view.MainMenuView;

import javax.swing.*;

public class GUIClient {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Checkers");

        var gameService = new GameService();
        var manager = frame.getLayout();

        var view = new MainMenuView(frame);
        var controller = new MainMenuController(view, new RulesetService(), gameService);
        controller.render();
    }
}
