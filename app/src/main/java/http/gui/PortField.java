package http.gui;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class PortField {

    private static final int PORT_FIELD_SIZE = 3;

    JLabel portLabel = new JLabel(GuiUtils.PORT_STRING);
    JTextField portTextField = new JTextField(PORT_FIELD_SIZE);

    public PortField() {
        portLabel.setLabelFor(portTextField);
    }

    public JLabel getPortLabel() {
        return portLabel;
    }

    public JTextField getPortTextField() {
        return portTextField;
    }

    public void setPortLabel(JLabel portLabel) {
        this.portLabel = portLabel;
    }

    public void setPortTextField(JTextField portTextField) {
        this.portTextField = portTextField;
    }
}
