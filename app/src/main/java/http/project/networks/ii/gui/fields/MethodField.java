package http.project.networks.ii.gui.fields;

import javax.swing.JLabel;
import javax.swing.JComboBox;

import http.project.networks.ii.gui.utils.GuiUtils;
import http.project.networks.ii.utils.Verbs;

/**
 * A field for selecting the method of the request
 */
public class MethodField {

    JLabel methodLabel;
    JComboBox<String> methodComboBox;
    BodyField bodyField;

    /**
     * Create a new MethodField
     */
    public MethodField() {
        super();
        methodLabel = new JLabel(GuiUtils.METHOD_STRING);
        methodComboBox = new JComboBox<>();
        bodyField = new BodyField();
        for (Verbs method: Verbs.values()) {
            methodComboBox.addItem(method.toString());
            methodComboBox.addItemListener(
                new MethodComboBoxListener(method.toString(), bodyField));
        }
    }

    /**
     * Get the body field
     * @return the body field
     */
    public BodyField getBodyField() {
        return bodyField;
    }

    /**
     * Get the label for the method
     * @return the label for the method
     */
    public JLabel getMethodLabel() {
        return methodLabel;
    }

    /**
     * Get the selected method as a JComboBox of Strings
     * @return the selected method
     */
    public JComboBox<String> getMethodComboBox() {
        return methodComboBox;
    }

    /**
     * Get the selected method as a Verbs object
     * @return the selected method
     */
    public Verbs getSelectedMethod() {
        return Verbs.valueOf((String) methodComboBox.getSelectedItem());
    }
    
    /**
     * Set the body field
     * @param bodyField the body field
     */
    public void setBodyField(BodyField bodyField) {
        this.bodyField = bodyField;
    }

    /**
     * Set the label for the method
     * @param methodLabel the label for the method
     */
    public void setMethodLabel(JLabel methodLabel) {
        this.methodLabel = methodLabel;
    }

    /**
     * Set the selected method
     * @param method the selected method
     */
    public void setSelectedMethod(Verbs method) {
        methodComboBox.setSelectedItem(method);
    }

    /**
     * Set the combo box for the method
     * @param methodComboBox the combo box for the method
     */
    public void setMethodComboBox(JComboBox<String> methodComboBox) {
        this.methodComboBox = methodComboBox;
    }
}
