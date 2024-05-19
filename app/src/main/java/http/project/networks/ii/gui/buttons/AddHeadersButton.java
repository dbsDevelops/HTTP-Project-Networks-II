package http.project.networks.ii.gui.buttons;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import http.project.networks.ii.gui.dialogs.HeadersDialog;
import http.project.networks.ii.gui.utils.GuiUtils;

/**
 * A button to open the HeadersDialog for the user to add the extra headers
 */
public class AddHeadersButton extends JButton {

    /**
     * Create a new AddHeadersButton
     * @param dialog The HeadersDialog to open when the button is clicked
     */
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
