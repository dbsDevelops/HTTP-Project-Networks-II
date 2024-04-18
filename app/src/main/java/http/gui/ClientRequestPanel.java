package http.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Container;

public class ClientRequestPanel extends JPanel {

    protected static final String HOST_STRING = "Host ";
    protected static final String PORT_STRING = "Port ";
    
    // Lay out the text controls and labels
    JLabel hostLabel = new JLabel(HOST_STRING);
    JLabel portLabel = new JLabel(PORT_STRING);
    JTextField hostField = new JTextField(40);
    JTextField portField = new JTextField(3);

    JPanel requestControlsPanel = new JPanel();
    GridBagLayout gridbag = new GridBagLayout();
    GridBagConstraints c; 

    JPanel responseControlsPanel = new JPanel();

    public ClientRequestPanel() {
        // Add components

        // Set up left panel (requests)
        JLabel[] labels = {hostLabel, portLabel};
        JTextField[] textFields = {hostField, portField};
        addLabelTextRows(labels, textFields, requestControlsPanel);
        requestControlsPanel.setLayout(gridbag);
        add(requestControlsPanel);

        // Set up right panel (resoponses)
        responseControlsPanel.setLayout(gridbag);
    }

    private void addLabelTextRows(JLabel[] labels, JTextField[] textFields, Container container) {
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.EAST;
        int numLabels = labels.length;

        addLabelsToContainer(numLabels, labels, textFields, container);
    }

    private void addLabelsToContainer(int numLabels, 
                                        JLabel[] labels, 
                                        JTextField[] textFields, 
                                        Container container) {
        for (int currentLabelIndex = 0; currentLabelIndex < numLabels; currentLabelIndex++) {
            c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
            c.fill = GridBagConstraints.NONE;      //reset to default
            c.weightx = 0.0;                       //reset to default
            container.add(labels[currentLabelIndex], c);

            c.gridwidth = GridBagConstraints.REMAINDER;     //end row
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1.0;
            container.add(textFields[currentLabelIndex], c);
        }
    }
}
