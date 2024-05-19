package http.project.networks.ii.gui.fields;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import http.project.networks.ii.gui.utils.GuiUtils;
import http.project.networks.ii.utils.HttpBodyType;

/**
 * A field for selecting the body type of the request
 */
public class BodyTypeField {

    JLabel bodyTypeLabel;
    JComboBox<String> bodyTypeComboBox;

    /**
     * Create a new BodyTypeField
     */
    public BodyTypeField() {
        bodyTypeLabel = new JLabel(GuiUtils.BODY_TYPE_STRING);
        bodyTypeComboBox = new JComboBox<>();
        this.addBodyTypesToComboBox();
        bodyTypeLabel.setLabelFor(bodyTypeComboBox);
    }
    
    /**
     * Get the label for the body type
     * @return the label for the body type
     */
    public JLabel getBodyTypeLabel() {
        return bodyTypeLabel;
    }

    /**
     * Get the selected body type
     * @return the selected body type
     */
    public HttpBodyType getSelectedBodyType() {
        return HttpBodyType.valueOf((String) bodyTypeComboBox.getSelectedItem());
    }

    /**
     * Get the combo box for the body type
     * @return the combo box for the body type
     */
    public JComboBox<String> getBodyTypeComboBox() {
        return bodyTypeComboBox;
    }

    /**
     * Set the label for the body type
     * @param bodyTypeLabel the label for the body type
     */
    public void setBodyTypeLabel(JLabel bodyTypeLabel) {
        this.bodyTypeLabel = bodyTypeLabel;
    }

    /**
     * Set the combo box for the body type
     * @param bodyTypeComboBox the combo box for the body type
     */
    public void setBodyTypeComboBox(JComboBox<String> bodyTypeComboBox) {
        this.bodyTypeComboBox = bodyTypeComboBox;
    }

    /**
     * Set the items in the combo box for the body type
     * @param items the items in the combo box for the body type
     */
    public void setBodyTypeComboBoxItems(String[] items) {
        for (String item : items) {
            bodyTypeComboBox.addItem(item);
        }
    }

    /**
     * Add the body types to the combo box
     */
    public void addBodyTypesToComboBox() {
        for (HttpBodyType bodyType : HttpBodyType.values()) {
            bodyTypeComboBox.addItem(bodyType.toString());
        }
    }
}
