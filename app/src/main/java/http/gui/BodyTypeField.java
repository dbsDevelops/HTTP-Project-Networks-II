package http.gui;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import http.project.networks.ii.HttpBodyType;

public class BodyTypeField {

    JLabel bodyTypeLabel;
    JComboBox<String> bodyTypeComboBox;

    public BodyTypeField() {
        bodyTypeLabel = new JLabel(GuiUtils.BODY_TYPE_STRING);
        bodyTypeComboBox = new JComboBox<>();
        this.addBodyTypesToComboBox();
        bodyTypeLabel.setLabelFor(bodyTypeComboBox);
    }
    
    public JLabel getBodyTypeLabel() {
        return bodyTypeLabel;
    }

    public JComboBox<String> getBodyTypeComboBox() {
        return bodyTypeComboBox;
    }

    public void setBodyTypeLabel(JLabel bodyTypeLabel) {
        this.bodyTypeLabel = bodyTypeLabel;
    }

    public void setBodyTypeComboBox(JComboBox<String> bodyTypeComboBox) {
        this.bodyTypeComboBox = bodyTypeComboBox;
    }

    public void setBodyTypeComboBoxItems(String[] items) {
        for (String item : items) {
            bodyTypeComboBox.addItem(item);
        }
    }

    public void addBodyTypesToComboBox() {
        for (HttpBodyType bodyType : HttpBodyType.values()) {
            bodyTypeComboBox.addItem(bodyType.toString());
        }
    }
}
