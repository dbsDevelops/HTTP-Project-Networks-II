package http.project.networks.ii.gui.fields;

import javax.swing.JLabel;
import javax.swing.JComboBox;

import http.project.networks.ii.gui.GuiUtils;
import http.project.networks.ii.utils.Verbs;

public class MethodField {

    JLabel methodLabel;
    JComboBox<String> methodComboBox;

    public MethodField() {
        super();
        methodLabel = new JLabel(GuiUtils.METHOD_STRING);
        methodComboBox = new JComboBox<>();
        for (Verbs method: Verbs.values()) {
            methodComboBox.addItem(method.toString());
        }
    }

    public JLabel getMethodLabel() {
        return methodLabel;
    }

    public JComboBox<String> getMethodComboBox() {
        return methodComboBox;
    }

    public Verbs getSelectedMethod() {
        return Verbs.valueOf((String) methodComboBox.getSelectedItem());
    }
    
    public void setMethodLabel(JLabel methodLabel) {
        this.methodLabel = methodLabel;
    }

    public void setSelectedMethod(Verbs method) {
        methodComboBox.setSelectedItem(method);
    }

    public void setMethodComboBox(JComboBox<String> methodComboBox) {
        this.methodComboBox = methodComboBox;
    }
}
