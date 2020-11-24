package guicheckers;

import api.services.RulesetService;
import guicheckers.controller.MainMenuController;
import guicheckers.view.MainMenuView;

import javax.swing.*;

public class GUIClient {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Checkers");

        var rulesetService = new RulesetService();

        var view = new MainMenuView(frame);
        new MainMenuController(view, rulesetService);
        view.render();
    }
}
