package http.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

public class ClientRequestPanel extends JPanel {
    
    HostField hostField = new HostField();
    PortField portField = new PortField();
    HeadersDialog headersDialog = new HeadersDialog();
    RequestAndResponseSplitPanel requestAndResponseSplitPanel = new RequestAndResponseSplitPanel();
    SendRequestButton sendRequestButton = new SendRequestButton();

    JScrollPane responseControlsPanel = new JScrollPane();

    public ClientRequestPanel() {
        setLayout(new BorderLayout());
        // Add components

        // Set up left panel (requests)
        JLabel[] labels = {hostField.getHostLabel(), portField.getPortLabel()};
        JTextField[] textFields = {hostField.getHostTextField(), portField.getPortTextField()};
        addLabelTextRows(labels, textFields, requestAndResponseSplitPanel.getRequestControlsPanel());
        add(requestAndResponseSplitPanel, BorderLayout.CENTER);
        add(sendRequestButton, BorderLayout.SOUTH);

        headersDialog.setTitle("Extra Headers");
        headersDialog.setSize(new Dimension(GuiUtils.DIALOG_WIDTH, GuiUtils.DIALOG_HEIGHT));
    }

    private void addLabelTextRows(JLabel[] labels, JTextField[] textFields, Container container) {
        int numLabels = labels.length;
        addLabelsToContainer(numLabels, labels, textFields, container);
    }

    private void addLabelsToContainer(int numLabels, 
                                        JLabel[] labels, 
                                        JTextField[] textFields, 
                                        Container container) {
        for (int currentLabelIndex = 0; currentLabelIndex < numLabels; currentLabelIndex++) {
            labels[currentLabelIndex].setLabelFor(textFields[currentLabelIndex]);
            container.add(labels[currentLabelIndex]);
            container.add(textFields[currentLabelIndex]);
        }
    }
}
