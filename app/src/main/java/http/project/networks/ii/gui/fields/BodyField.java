package http.project.networks.ii.gui.fields;

import javax.swing.JLabel;
import javax.swing.JTextArea;

public class BodyField {

    private static final String BODY_CONTENT = "Body Content:";

    JLabel bodyContentLabel;
    JTextArea bodyContentTextArea;

    public BodyField() {
        bodyContentLabel = new JLabel(BODY_CONTENT);
        bodyContentTextArea = new JTextArea();
        bodyContentLabel.setLabelFor(bodyContentTextArea);
    }

    public JLabel getBodyContentLabel() {
        return bodyContentLabel;
    }

    public JTextArea getBodyContentTextArea() {
        return bodyContentTextArea;
    }

    public String getBodyContent() {
        return bodyContentTextArea.getText();
    }

    public void setBodyContentLabel(JLabel bodyContentLabel) {
        this.bodyContentLabel = bodyContentLabel;
    }

    public void setBodyContentTextArea(JTextArea bodyContentTextArea) {
        this.bodyContentTextArea = bodyContentTextArea;
    }
}
