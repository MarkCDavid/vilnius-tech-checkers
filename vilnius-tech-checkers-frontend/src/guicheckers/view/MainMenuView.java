package guicheckers.view;

import api.dto.Ruleset;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class MainMenuView extends AbstractView {

    public JList<Ruleset> getAvailableRulesList() {
        return availableRules;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public void setAvailableRules(Collection<Ruleset> rulesets) {
        var list = getAvailableRulesList();
        var model = new DefaultListModel<Ruleset>();

        model.addAll(rulesets);
        list.setModel(model);

        if (!model.isEmpty()) {
            list.setSelectedIndex(0);
        }
    }

    private void createUIComponents() {
        this.availableRules = new JList<>();
    }

    public MainMenuView(JFrame parent) {
        super(parent);
    }

    @Override
    protected Container getRootContainer() {
        return mainPanel;
    }

    private JPanel mainPanel;
    private JButton startButton;
    private JButton exitButton;
    private JList<Ruleset> availableRules;
}

