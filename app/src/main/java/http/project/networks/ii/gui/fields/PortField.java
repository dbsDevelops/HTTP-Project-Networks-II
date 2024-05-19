package http.project.networks.ii.gui.fields;

import javax.swing.JLabel;
import javax.swing.JTextField;

import http.project.networks.ii.gui.utils.GuiUtils;

/**
 * A field for the port of the request
 */
public class PortField {

    private static final int PORT_FIELD_SIZE = 3;

    JLabel portLabel = new JLabel(GuiUtils.PORT_STRING);
    JTextField portTextField = new JTextField(PORT_FIELD_SIZE);

    /**
     * Create a new PortField
     */
    public PortField() {
        portLabel.setLabelFor(portTextField);
    }

    /**
     * Get the label for the port
     * @return the label for the port
     */
    public JLabel getPortLabel() {
        return portLabel;
    }

    /**
     * Get the text field for the port
     * @return the text field for the port
     */
    public JTextField getPortTextField() {
        return portTextField;
    }

    /**
     * Get the port
     * @return the port
     */
    public int getPort() {
        return Integer.parseInt(portTextField.getText());
    }

    /**
     * Set the port
     * @param port the port
     */
    public void setPortLabel(JLabel portLabel) {
        this.portLabel = portLabel;
    }

    /**
     * Set the port
     * @param port the port
     */
    public void setPortTextField(JTextField portTextField) {
        this.portTextField = portTextField;
    }
}
