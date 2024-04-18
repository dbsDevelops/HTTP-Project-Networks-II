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
import java.awt.CheckboxGroup;

public class ClientRequestPanel extends JPanel {

    protected static final String HOST_STRING = "Host ";
    protected static final String PORT_STRING = "Port ";
    protected static final String HEADERS_STRING = "Headers";
    protected static final int ROWS_AND_COLUMNS = 4;
    
    // Lay out the text controls and labels
    JLabel hostLabel = new JLabel(HOST_STRING);
    JLabel portLabel = new JLabel(PORT_STRING);
    JLabel headersLabel = new JLabel(HEADERS_STRING);
    JTextField hostField = new JTextField(40);
    JTextField portField = new JTextField(3);
    ArrayList<Checkbox> headerCheckboxes = new ArrayList<>();
    JButton addHeadersButton = new JButton("Add Headers");
    JDialog addHeadersDialog = new JDialog();
    JButton sendButton = new JButton("Send");

    JPanel requestControlsPanel = new JPanel();
    GridLayout gridLayout = new GridLayout(ROWS_AND_COLUMNS, ROWS_AND_COLUMNS);

    JScrollPane responseControlsPanel = new JScrollPane();

    public ClientRequestPanel() {
        setLayout(new BorderLayout());
        // Add components

        // Set up left panel (requests)
        JLabel[] labels = {hostLabel, portLabel};
        JTextField[] textFields = {hostField, portField};
        requestControlsPanel.setLayout(gridLayout);
        addLabelTextRows(labels, textFields, requestControlsPanel);

        addHeadersButton.addActionListener(new ActionListener() {
           public void actionPerformed(java.awt.event.ActionEvent e) {
               addHeaderCheckboxes();
               addHeadersDialog.setTitle("Extra Headers");
               
               addHeadersDialog.setVisible(true);
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
}
