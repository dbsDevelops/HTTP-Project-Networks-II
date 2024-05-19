package http.project.networks.ii.gui.fields;

import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * A field for the body content of the request
 */
public class BodyField {

    private static final String BODY_CONTENT = "Body Content:";

    JLabel bodyContentLabel;
    JTextArea bodyContentTextArea;

    /**
     * Create a new BodyField
     */
    public BodyField() {
        bodyContentLabel = new JLabel(BODY_CONTENT);
        bodyContentTextArea = new JTextArea();
        bodyContentLabel.setLabelFor(bodyContentTextArea);
    }

    /**
     * Get the label for the body content
     * @return the label for the body content
     */
    public JLabel getBodyContentLabel() {
        return bodyContentLabel;
    }

    /**
     * Get the text area for the body content
     * @return the text area for the body content
     */
    public JTextArea getBodyContentTextArea() {
        return bodyContentTextArea;
    }

    /**
     * Get the body content
     * @return the body content
     */
    public String getBodyContent() {
        return bodyContentTextArea.getText();
    }

    /**
     * Set the body content
     * @param bodyContentLabel the body content
     */
    public void setBodyContentLabel(JLabel bodyContentLabel) {
        this.bodyContentLabel = bodyContentLabel;
    }

    /**
     * Set the body content
     * @param bodyContentTextArea the body content
     */
    public void setBodyContentTextArea(JTextArea bodyContentTextArea) {
        this.bodyContentTextArea = bodyContentTextArea;
    }
}
