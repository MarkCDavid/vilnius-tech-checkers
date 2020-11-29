package guicheckers.controller;

import api.endpoints.GameLaunchService;
import api.endpoints.RulesetService;
import api.services.GameService;
import guicheckers.view.GameView;
import guicheckers.view.MainMenuView;

import java.awt.event.ActionListener;
import java.util.List;

public class MainMenuController extends AbstractController<MainMenuView> {

    private final RulesetService rulesetService;
    private final GameLaunchService launchService;

    public MainMenuController(MainMenuView view, RulesetService rulesetService, GameLaunchService launchService) {
        super(view);
        this.rulesetService = rulesetService;
        this.launchService = launchService;
        view.setAvailableRules(rulesetService.getRulesets());
        view.getStartButton().addActionListener(startButtonAction());
        view.getExitButton().addActionListener(exitButtonAction());
    }

    public ActionListener exitButtonAction() {
        return e -> System.exit(0);
    }

    public ActionListener startButtonAction() {
        return e -> {
            var selectedRuleset = getView().getAvailableRulesList().getSelectedValue();
            var launchData = launchService.launchLocal(selectedRuleset, List.of("Player 1", "Player 2"));

            var view = new GameView(getView().getFrame());
            new GameViewController(view, launchData,  (GameService)launchService, (GameService)launchService);
            view.render();
        };
    }

}
