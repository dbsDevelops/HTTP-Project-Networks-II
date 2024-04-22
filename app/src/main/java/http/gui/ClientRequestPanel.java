package http.gui;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import http.project.networks.ii.HttpHeaders;

import javax.swing.BorderFactory;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JDialog;

import java.awt.GridLayout;
import java.awt.Checkbox;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

public class ClientRequestPanel extends JPanel {
    
    HostField hostField = new HostField();
    PortField portField = new PortField();
    JLabel headersLabel = new JLabel(GuiUtils.HEADERS_STRING);
    ArrayList<Checkbox> headerCheckboxes = new ArrayList<>();
    JButton addHeadersButton = new JButton(GuiUtils.ADD_HEADERS_STRING);
    JDialog addHeadersDialog = new JDialog();
    JButton sendButton = new JButton(GuiUtils.SEND_STRING);

    JPanel requestControlsPanel = new JPanel();
    GridLayout gridLayout = new GridLayout(GuiUtils.ROWS_AND_COLUMNS, GuiUtils.ROWS_AND_COLUMNS);

    JScrollPane responseControlsPanel = new JScrollPane();

    public ClientRequestPanel() {
        setLayout(new BorderLayout());
        // Add components

        // Set up left panel (requests)
        JLabel[] labels = {hostField.getHostLabel(), portField.getPortLabel()};
        JTextField[] textFields = {hostField.getHostTextField(), portField.getPortTextField()};
        requestControlsPanel.setLayout(gridLayout);
        addLabelTextRows(labels, textFields, requestControlsPanel);

        addHeaderCheckboxes();

        addHeadersDialog.setTitle("Extra Headers");
        addHeadersDialog.setSize(new Dimension(GuiUtils.DIALOG_WIDTH, GuiUtils.DIALOG_HEIGHT));
        addHeaderCheckboxesToDialog();
        addHeadersButton.addActionListener(new ActionListener() {
           public void actionPerformed(java.awt.event.ActionEvent e) {
            if (!addHeadersDialog.isVisible()){
                addHeadersDialog.setVisible(true);
            }
           }
        });

        requestControlsPanel.add(addHeadersButton);
        requestControlsPanel.add(sendButton);
        //requestControlsPanel.setPreferredSize(new Dimension(500, 300));
        requestControlsPanel.setMinimumSize(new Dimension(300, 100));
        requestControlsPanel.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Request"),
                BorderFactory.createEmptyBorder(5,5,5,5)
            )
        );

        // Set up right panel (resoponses)
        //responseControlsPanel.setLayout(new BorderLayout());
        //requestControlsPanel.setPreferredSize(new Dimension(500, 300));
        responseControlsPanel.setMinimumSize(new Dimension(300, 100));
        responseControlsPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        responseControlsPanel.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Response"),
                BorderFactory.createEmptyBorder(5,5,5,5)
            )
        );
        
        // Set up split panel
        JSplitPane splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, requestControlsPanel, responseControlsPanel);
        splitPanel.setOneTouchExpandable(true);
        splitPanel.setResizeWeight(0.5);
        add(splitPanel);
    }

    private void addLabelTextRows(JLabel[] labels, JTextField[] textFields, Container container) {
        // c = new GridBagConstraints();
        // c.anchor = GridBagConstraints.EAST;
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

    public void addHeaderCheckboxes() {
        for (String header: HttpHeaders.getHeaders()) {
            Checkbox headerCheckbox = new Checkbox(header);
            headerCheckboxes.add(headerCheckbox);
        }
    }

    public void addHeaderCheckboxesToDialog() {
        for (Checkbox headerCheckbox: headerCheckboxes) {
            addHeadersDialog.add(headerCheckbox);
        }
    }
}
