package guicheckers;

import backend.rulesets.CheckersRuleset;
import backend.rulesets.fourplayer.FourPlayerCheckers;
import backend.rulesets.english.EnglishCheckers;
import backend.rulesets.turkish.TurkishCheckers;

import javax.swing.*;

public class MainMenuForm {

    private final JFrame parent;
    private JPanel mainPanel;
    private JButton startButton;
    private JButton exitButton;
    private JList<CheckersRuleset> availableRules;

    public MainMenuForm(JFrame parent) {
        this.parent = parent;

        startButton.addActionListener(e -> GameForm.show(parent, availableRules.getSelectedValue()));
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void createUIComponents() {

        DefaultListModel<CheckersRuleset> availableRulesModel = new DefaultListModel<>();

        availableRulesModel.addElement(new EnglishCheckers());
//        availableRulesModel.addElement(new ItalianCheckers());
        availableRulesModel.addElement(new TurkishCheckers());
//        availableRulesModel.addElement(new InternationalCheckers());
        availableRulesModel.addElement(new FourPlayerCheckers());

        availableRules = new JList<>(availableRulesModel);
        availableRules.setSelectedIndex(0);
    }


    public static void show(JFrame frame) {
        MainMenuForm form = new MainMenuForm(frame);
        frame.setContentPane(form.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}


/* DATA MODELING
    Requires:
        - Available Rulesets
 */

