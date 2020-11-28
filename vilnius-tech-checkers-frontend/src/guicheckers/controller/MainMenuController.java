package guicheckers.controller;

import api.endpoints.RulesetService;
import api.services.GameLaunchService;
import guicheckers.view.GameView;
import guicheckers.view.MainMenuView;

import java.awt.event.ActionListener;

public class MainMenuController extends AbstractController<MainMenuView> {


    public MainMenuController(MainMenuView view, RulesetService rulesetService) {
        super(view);
        view.setAvailableRules(rulesetService.getRulesets());
        view.getStartButton().addActionListener(startButtonAction());
        view.getExitButton().addActionListener(exitButtonAction());
    }

    public ActionListener exitButtonAction() {
        return e -> System.exit(0);
    }

    public ActionListener startButtonAction() {
        return e -> {
            var view = new GameView(getView().getFrame(), getView().getAvailableRulesList().getSelectedValue(), new GameLaunchService());
            new GameViewController(view);
            view.render();
        };
    }

}
