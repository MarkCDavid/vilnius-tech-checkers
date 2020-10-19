package example;


import api.dto.Ruleset;

import javax.swing.*;

public class RulesetListView {

    public JList<Ruleset> getList() {
        return list;
    }

    private JList<Ruleset> list;
}
