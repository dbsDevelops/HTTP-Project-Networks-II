package http.gui;

import java.awt.event.ActionListener;

import javax.swing.JButton;

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
