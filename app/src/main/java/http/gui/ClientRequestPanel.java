package http.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JSplitPane;
import javax.swing.JComboBox;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

public class ClientRequestPanel extends JPanel {

    protected static final String HOST_STRING = "Host ";
    protected static final String PORT_STRING = "Port ";
    protected static final String HEADERS_STRING = "Headers";
    
    // Lay out the text controls and labels
    JLabel hostLabel = new JLabel(HOST_STRING);
    JLabel portLabel = new JLabel(PORT_STRING);
    JLabel headersLabel = new JLabel(HEADERS_STRING);
    JTextField hostField = new JTextField(40);
    JTextField portField = new JTextField(3);
    JComboBox<String> headersComboBox = new JComboBox<>();

    JPanel requestControlsPanel = new JPanel();
    GridLayout gridLayout = new GridLayout(2,2);

    JPanel responseControlsPanel = new JPanel();

    public ClientRequestPanel() {
        setLayout(new BorderLayout());
        // Add components

        // Set up left panel (requests)
        JLabel[] labels = {hostLabel, portLabel};
        JTextField[] textFields = {hostField, portField};
        requestControlsPanel.setLayout(gridLayout);
        addLabelTextRows(labels, textFields, requestControlsPanel);
        requestControlsPanel.setPreferredSize(new Dimension(500, 300));
        requestControlsPanel.setMinimumSize(new Dimension(300, 100));
        requestControlsPanel.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Request"),
                BorderFactory.createEmptyBorder(5,5,5,5)
            )
        );
        add(requestControlsPanel);

        // Set up right panel (resoponses)
        responseControlsPanel.setLayout(new BorderLayout());
        requestControlsPanel.setPreferredSize(new Dimension(500, 300));
        requestControlsPanel.setMinimumSize(new Dimension(300, 100));
        responseControlsPanel.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Response"),
                BorderFactory.createEmptyBorder(5,5,5,5)
            )
        );
        
        // Set up split panel
        JSplitPane splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, requestControlsPanel, responseControlsPanel);
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
}
