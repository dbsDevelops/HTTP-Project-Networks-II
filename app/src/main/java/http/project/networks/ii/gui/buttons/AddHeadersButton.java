package http.project.networks.ii.gui.buttons;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import http.project.networks.ii.gui.GuiUtils;
import http.project.networks.ii.gui.dialogs.HeadersDialog;

public class AddHeadersButton extends JButton {

    public AddHeadersButton(HeadersDialog dialog) {
        super(GuiUtils.ADD_HEADERS_STRING);
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dialog.setVisible(true);
            }
        });
    }

}
