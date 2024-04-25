package http.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JTextField;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;

import javax.swing.WindowConstants;

import http.project.networks.ii.HttpHeaders;

public class HeadersDialog extends JDialog {
    CheckboxGroup checkboxGroup = new CheckboxGroup();
    ArrayList<Checkbox> headerCheckboxes = new ArrayList<>();
    ArrayList<JTextField> headerTextFields = new ArrayList<>();

    public HeadersDialog() {
        super();
        this.setTitle(GuiUtils.ADD_HEADERS_STRING);
        this.setSize(GuiUtils.DIALOG_WIDTH, GuiUtils.DIALOG_HEIGHT);
        this.setResizable(true);
        this.addHeadersToCheckboxes();
        this.addHeaderCheckboxesToDialog();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void addHeadersToCheckboxes() {
        for (HttpHeaders header : HttpHeaders.values()) {
            Checkbox checkbox = new Checkbox(header.getHeader());
            headerCheckboxes.add(checkbox);
        }
    }

    public void addHeaderCheckboxesToDialog() {
        for (Checkbox checkbox : headerCheckboxes) {
            checkbox.setCheckboxGroup(checkboxGroup);
            checkbox.setBounds(10, 10, 5, 5);
            this.add(checkbox);
        }
    }

    // public void addTextFieldsToCheckboxes

    public List<Checkbox> getHeaderCheckboxes() {
        return headerCheckboxes;
    }

}
