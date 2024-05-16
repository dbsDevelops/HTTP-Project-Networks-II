package http.project.networks.ii.gui.panels;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import http.project.networks.ii.gui.buttons.AddHeadersButton;
import http.project.networks.ii.gui.dialogs.HeadersDialog;
import http.project.networks.ii.gui.fields.BodyField;
import http.project.networks.ii.gui.fields.BodyTypeField;
import http.project.networks.ii.gui.fields.HostField;
import http.project.networks.ii.gui.fields.MethodField;
import http.project.networks.ii.gui.fields.PortField;
import http.project.networks.ii.gui.utils.GuiUtils;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Container;
import java.util.List;
import java.util.ArrayList;

public class RequestControlsPanel extends JPanel {

    private static final int FIRST_LABEL_INDEX = 0;
    private static final int PANEL_WIDTH = 300;
    private static final int PANEL_HEIGHT = 300;

    private transient HostField hostField;
    private transient PortField portField;
    private transient BodyTypeField bodyTypeField;
    private transient BodyField bodyField;
    private transient MethodField methodField;
    private HeadersDialog headersDialog;
    private AddHeadersButton addHeadersButton;
    
    private GridLayout gridLayout = new GridLayout(GuiUtils.ROWS, GuiUtils.COLUMNS);

    public RequestControlsPanel() {
        super();
        this.hostField = new HostField();
        this.portField = new PortField();
        this.bodyTypeField = new BodyTypeField();
        this.bodyField = new BodyField();
        this.methodField = new MethodField();
        this.headersDialog = new HeadersDialog();
        this.addHeadersButton = new AddHeadersButton(headersDialog);
        configurePanel();
        addComponents();
    }

    public void configurePanel() {
        this.setLayout(gridLayout);
        this.setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(GuiUtils.REQUEST_STRING),
                BorderFactory.createEmptyBorder(GuiUtils.BORDER_WIDTH, GuiUtils.BORDER_WIDTH, GuiUtils.BORDER_WIDTH, GuiUtils.BORDER_WIDTH)
        ));
    }

    public void addLabelsAndTextFields(JLabel[] labels, JTextField[] textFields) {
        addLabelTextRows(labels, textFields, this);
    }

    public void addLabelsAndTextAreas(JLabel[] labels, JTextArea[] textAreas) {
        //addLabelTextRows(labels, textAreas, this);
    }

    public void addButtons() {
        this.add(addHeadersButton);
        //this.add(sendRequestButton);
    }

    public void addComponents() {
        addTextFields();
        addBodyField();
        addComboBoxes();
        addButtons();
    }

    private void addBodyField() {
        JLabel textLabels = bodyField.getBodyContentLabel();
        JTextArea textArea = bodyField.getBodyContentTextArea();
        addLabelsAndTextAreaFields(textLabels, textArea);
    }

    private void addLabelsAndTextAreaFields(JLabel textLabel, JTextArea textArea) {
        textLabel.setLabelFor(textArea);
        this.add(textLabel);
        this.add(textArea);
    }

    private void addTextFields() {
        JLabel[] textLabels = {hostField.getHostLabel(), portField.getPortLabel()};
        JTextField[] textFields = {hostField.getHostTextField(), portField.getPortTextField()};
        addLabelsAndTextFields(textLabels, textFields);
    }

    private void addComboBoxes() {
        List<JLabel> comboBoxLabels = new ArrayList<>();
        comboBoxLabels.add(methodField.getMethodLabel());
        comboBoxLabels.add(bodyTypeField.getBodyTypeLabel());
        List<JComboBox<String>> comboBoxes = new ArrayList<>();
        comboBoxes.add(methodField.getMethodComboBox());
        comboBoxes.add(bodyTypeField.getBodyTypeComboBox());
        addLabelComboBoxRows(comboBoxLabels, comboBoxes, this);
    }

    private void addLabelTextRows(JLabel[] labels, JTextField[] textFields, Container container) {
        int numLabels = labels.length;
        addLabelsToContainer(numLabels, labels, textFields, container);
    }

    private void addLabelsToContainer(int numLabels, 
                                        JLabel[] labels, 
                                        JTextField[] textFields, 
                                        Container container) {
        for (int currentLabelIndex = FIRST_LABEL_INDEX; currentLabelIndex < numLabels; currentLabelIndex++) {
            labels[currentLabelIndex].setLabelFor(textFields[currentLabelIndex]);
            container.add(labels[currentLabelIndex]);
            container.add(textFields[currentLabelIndex]);
        }
    }

    private void addLabelComboBoxRows(List<JLabel> labels, List<JComboBox<String>> comboBoxes, Container container) {
        int numLabels = labels.size();
        for (int currentLabelIndex = FIRST_LABEL_INDEX; currentLabelIndex < numLabels; currentLabelIndex++) {
            labels.get(currentLabelIndex).setLabelFor(comboBoxes.get(currentLabelIndex));
            container.add(labels.get(currentLabelIndex));
            container.add(comboBoxes.get(currentLabelIndex));
        }
    }

    public HostField getHostField() {
        return hostField;
    }

    public PortField getPortField() {
        return portField;
    }

    public BodyTypeField getBodyTypeField() {
        return bodyTypeField;
    }

    public MethodField getMethodField() {
        return methodField;
    }

    public AddHeadersButton getAddHeadersButton() {
        return addHeadersButton;
    }

    public HeadersDialog getHeadersDialog() {
        return headersDialog;
    }
}
