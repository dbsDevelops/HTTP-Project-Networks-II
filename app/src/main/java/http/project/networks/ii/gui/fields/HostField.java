package http.project.networks.ii.gui.fields;

import javax.swing.JLabel;
import javax.swing.JTextField;

import http.project.networks.ii.gui.utils.GuiUtils;

/**
 * A field for the host of the request
 */
public class HostField {

    private static final int HOST_FIELD_SIZE = 40;

    JLabel hostLabel = new JLabel(GuiUtils.HOST_STRING);
    JTextField hostTextField = new JTextField(HOST_FIELD_SIZE);

    /**
     * Create a new HostField
     */
    public HostField() {
        hostLabel.setLabelFor(hostTextField);
    }

    /**
     * Get the label for the host
     * @return the label for the host
     */
    public JLabel getHostLabel() {
        return hostLabel;
    }

    /**
     * Get the text field for the host
     * @return the text field for the host
     */
    public JTextField getHostTextField() {
        return hostTextField;
    }

    /**
     * Get the host
     * @return the host
     */
    public String getHost() {
        return hostTextField.getText();
    }

    /**
     * Set the host
     * @param host the host
     */
    public void setHostLabel(JLabel hostLabel) {
        this.hostLabel = hostLabel;
    }

    /**
     * Set the host
     * @param host the host
     */
    public void setHostTextField(JTextField hostTextField) {
        this.hostTextField = hostTextField;
    }
}
