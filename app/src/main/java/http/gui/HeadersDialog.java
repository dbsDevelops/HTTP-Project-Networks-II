package http.gui;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.Checkbox;
import javax.swing.WindowConstants;

public class HeadersDialog extends JDialog {
    ArrayList<Checkbox> headerCheckboxes = new ArrayList<>();

    public HeadersDialog() {
        super();
        this.setTitle(GuiUtils.ADD_HEADERS_STRING);
        this.setSize(GuiUtils.DIALOG_WIDTH, GuiUtils.DIALOG_HEIGHT);
        this.setResizable(false);
        this.addHeaderCheckboxesToDialog();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void addHeaderCheckboxesToDialog() {
        for (Checkbox checkbox : headerCheckboxes) {
            this.add(checkbox);
        }
    }
}
