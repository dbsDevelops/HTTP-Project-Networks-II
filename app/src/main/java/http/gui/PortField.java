package http.gui;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class PortField {
    JLabel portLabel = new JLabel(GuiUtils.PORT_STRING);
    JTextField portTextField = new JTextField(3);

    public PortField() {
        portLabel.setLabelFor(portTextField);
    }

    public JLabel getPortLabel() {
        return portLabel;
    }

    public JTextField getPortTextField() {
        return portTextField;
    }
}
