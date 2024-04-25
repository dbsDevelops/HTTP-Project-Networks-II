package http.gui;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class HostField {

    private static final int HOST_FIELD_SIZE = 40;

    JLabel hostLabel = new JLabel(GuiUtils.HOST_STRING);
    JTextField hostTextField = new JTextField(HOST_FIELD_SIZE);

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
