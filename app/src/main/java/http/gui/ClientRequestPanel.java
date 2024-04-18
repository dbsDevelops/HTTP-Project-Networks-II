package http.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientRequestPanel extends JPanel implements ActionListener {

    protected static final String HOST_STRING = "Host ";
    protected static final String PORT_STRING = "Port ";
    
    JLabel hostLabel;
    JLabel portLabel;
    JTextField hostField;
    JTextField portField;

    public ClientRequestPanel() {
        // Set up layout
        setLayout(new BorderLayout());
        // Add components
        addHostAndPortLabels();
        addHostAndPortFields();
        
    }

    public void addHostAndPortLabels() {
        hostLabel = new JLabel(HOST_STRING);
        portLabel = new JLabel(PORT_STRING);
        add(hostLabel);
        add(portLabel);
    }

    public void addHostAndPortFields() {
        hostField = new JTextField();
        portField = new JTextField();
        add(hostField);
        add(portField);
    }

    public void attachHostAndPortLabelsToFields() {
        hostLabel.setLabelFor(hostField);
        portLabel.setLabelFor(portField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}
