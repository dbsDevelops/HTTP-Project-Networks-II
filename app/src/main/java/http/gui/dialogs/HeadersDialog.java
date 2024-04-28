package http.gui.dialogs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import java.awt.GridLayout;
import java.awt.event.WindowListener;

import http.gui.GuiUtils;
import http.project.networks.ii.HttpHeaders;
import http.project.networks.ii.Request;
import http.project.networks.ii.RequestHeaders;

public class HeadersDialog extends JDialog {
    private static final int NUMBER_OF_HEADERS = HttpHeaders.values().length;
    private static final int NUMBER_OF_FIELDS = 2;
    
    private ArrayList<JCheckBox> headerCheckboxes = new ArrayList<>();
    private ArrayList<JTextField> headerTextFields = new ArrayList<>();
    private RequestHeaders headers = new RequestHeaders();

    public HeadersDialog() {
        super();
        this.setTitle(GuiUtils.ADD_HEADERS_STRING);
        //this.setLayout(new GridLayout(NUMBER_OF_HEADERS, NUMBER_OF_FIELDS));
        this.setLayout(new GridLayout(NUMBER_OF_HEADERS, 1));
        this.setSize(GuiUtils.DIALOG_WIDTH, GuiUtils.DIALOG_HEIGHT);
        this.setResizable(Boolean.TRUE);
        this.initUI();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                headers = null;
            }

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
            }

            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                headers = getSelectedHeaders();
            }

            @Override
            public void windowIconified(java.awt.event.WindowEvent e) {
            }

            @Override
            public void windowDeiconified(java.awt.event.WindowEvent e) {
            }

            @Override
            public void windowActivated(java.awt.event.WindowEvent e) {
            }

            @Override
            public void windowDeactivated(java.awt.event.WindowEvent e) {
            }
        });
    }

    private void initUI() {
        for (HttpHeaders header : HttpHeaders.values()) {
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new GridLayout(1, 1));
            rowPanel.setSize(10,10);
            
            JCheckBox checkBox = new JCheckBox(header.getHeader());
            JTextField textField = new JTextField(20);
            textField.setEnabled(false);  // Initially disable the text field
            
            // Listener to enable text field when checkbox is selected
            checkBox.addActionListener(e -> textField.setEnabled(checkBox.isSelected()));
            
            headerCheckboxes.add(checkBox);
            headerTextFields.add(textField);

            rowPanel.add(checkBox);
            rowPanel.add(textField);
            
            getContentPane().add(rowPanel);
        }
    }

    public RequestHeaders getSelectedHeaders() {
        RequestHeaders requestHeaders = new RequestHeaders();
        for (int headerIndex = 0; headerIndex < NUMBER_OF_HEADERS; headerIndex++) {
            if (headerCheckboxes.get(headerIndex).isSelected()) {
                requestHeaders.addHeaderToHeaders(HttpHeaders.values()[headerIndex], headerTextFields.get(headerIndex).getText());
                System.out.println("Header: " + HttpHeaders.values()[headerIndex] + " Value: " + headerTextFields.get(headerIndex).getText());
            }
        }
        return requestHeaders;
    }

    public RequestHeaders getHeaders() {
        return headers;
    }
}
