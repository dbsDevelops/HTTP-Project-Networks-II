package http.gui;

import javax.swing.JComboBox;

import http.project.networks.ii.Verbs;

public class MethodField extends JComboBox<String> {

    public MethodField() {
        super();
        for (Verbs method: Verbs.values()) {
            this.addItem(method.toString());
        }
    }
}
