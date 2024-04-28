package http.gui.buttons;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import http.gui.GuiUtils;
import http.gui.dialogs.HeadersDialog;

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
