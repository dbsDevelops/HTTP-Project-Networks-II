package http.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Container;

public class ClientRequestPanel extends JPanel {
    
    HeadersDialog headersDialog = new HeadersDialog();
    RequestAndResponseSplitPanel requestAndResponseSplitPanel = new RequestAndResponseSplitPanel();
    SendRequestButton sendRequestButton = new SendRequestButton();
    AddHeadersButton addHeadersButton = new AddHeadersButton(headersDialog);

    public ClientRequestPanel() {
        // Set the layout of the panel to BorderLayout
        setLayout(new BorderLayout());
        // Add components to the panel
        add(requestAndResponseSplitPanel, BorderLayout.CENTER);
    }
}
