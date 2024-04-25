package http.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.WindowConstants;

import http.project.networks.ii.HttpHeaders;

public class HeadersDialog extends JDialog {
    private static final int NUMBER_OF_HEADERS = HttpHeaders.values().length;
    private static final int NUMBER_OF_FIELDS = 2;
    
    ArrayList<JCheckBox> headerCheckboxes = new ArrayList<>();
    ArrayList<JTextField> headerTextFields = new ArrayList<>();
    //JScrollPane scrollPane;

    public HeadersDialog() {
        super();
        this.setTitle(GuiUtils.ADD_HEADERS_STRING);
        this.setLayout(new GridLayout(NUMBER_OF_HEADERS, NUMBER_OF_FIELDS));
        this.setSize(GuiUtils.DIALOG_WIDTH, GuiUtils.DIALOG_HEIGHT);
        this.setResizable(Boolean.TRUE);
        this.initUI();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(scrollPane); 

        for (HttpHeaders header : HttpHeaders.values()) {
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new GridLayout(1, 1));
            
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
}
