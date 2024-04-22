package http.gui;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class HostField {
    JLabel hostLabel = new JLabel(GuiUtils.HOST_STRING);
    JTextField hostTextField = new JTextField(40);

    public HostField() {
        hostLabel.setLabelFor(hostTextField);
    }

    public JLabel getHostLabel() {
        return hostLabel;
    }

    public JTextField getHostTextField() {
        return hostTextField;
    }
}
