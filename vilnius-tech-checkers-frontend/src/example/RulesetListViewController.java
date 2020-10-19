package example;

import api.dto.Ruleset;
import api.endpoints.RulesetService;

import javax.swing.*;

public class RulesetListViewController {

    public RulesetListViewController(RulesetService rulesetService, RulesetListView view) {
        this.view = view;
        ((DefaultListModel<Ruleset>)this.view.getList().getModel()).addAll(rulesetService.getRulesets());

    }


    private final RulesetListView view;

}
