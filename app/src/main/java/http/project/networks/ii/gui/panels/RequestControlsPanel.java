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
import http.project.networks.ii.gui.utils.GuiUtils;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Container;
import java.util.List;
import java.util.ArrayList;

/**
 * A panel for the controls of the request
 */
public class RequestControlsPanel extends JPanel {

    private static final int FIRST_LABEL_INDEX = 0;
    private static final int PANEL_WIDTH = 300;
    private static final int PANEL_HEIGHT = 300;

    private transient HostField hostField;
    private transient BodyTypeField bodyTypeField;
    private transient BodyField bodyField;
    private transient MethodField methodField;
    private HeadersDialog headersDialog;
    private AddHeadersButton addHeadersButton;
    
    private GridLayout gridLayout = new GridLayout(GuiUtils.ROWS, GuiUtils.COLUMNS);

    /**
     * Create a new RequestControlsPanel
     */
    public RequestControlsPanel() {
        super();
        this.hostField = new HostField();
        this.bodyTypeField = new BodyTypeField();
        this.bodyField = new BodyField();
        this.methodField = new MethodField();
        this.headersDialog = new HeadersDialog();
        this.addHeadersButton = new AddHeadersButton(headersDialog);
        configurePanel();
        addComponents();
    }

    /**
     * Configure the panel's layout, minimum size, and border
     */
    public void configurePanel() {
        this.setLayout(gridLayout);
        this.setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(GuiUtils.REQUEST_STRING),
                BorderFactory.createEmptyBorder(GuiUtils.BORDER_WIDTH, GuiUtils.BORDER_WIDTH, GuiUtils.BORDER_WIDTH, GuiUtils.BORDER_WIDTH)
        ));
    }

    /**
     * Add labels and text fields to the panel
     * @param labels the labels to add
     * @param textFields the text fields to add
     */
    public void addLabelsAndTextFields(JLabel[] labels, JTextField[] textFields) {
        addLabelTextRows(labels, textFields, this);
    }

    public void addLabelsAndTextAreas(JLabel[] labels, JTextArea[] textAreas) {
        //addLabelTextRows(labels, textAreas, this);
    }

    /**
     * Add buttons to the panel
     */
    public void addButtons() {
        this.add(addHeadersButton);
    }

    /**
     * Add components to the panel
     */
    public void addComponents() {
        addTextFields();
        addBodyField();
        addComboBoxes();
        addButtons();
    }

    /**
     * Add the body field to the panel
     */
    private void addBodyField() {
        JLabel textLabels = bodyField.getBodyContentLabel();
        JTextArea textArea = bodyField.getBodyContentTextArea();
        addLabelsAndTextAreaFields(textLabels, textArea);
    }

    /**
     * Add labels and text area fields to the panel
     * @param textLabel the label to add
     * @param textArea the text area to add
     */
    private void addLabelsAndTextAreaFields(JLabel textLabel, JTextArea textArea) {
        textLabel.setLabelFor(textArea);
        this.add(textLabel);
        this.add(textArea);
    }

    /**
     * Add text fields to the panel
     */
    private void addTextFields() {
        JLabel[] textLabels = {hostField.getHostLabel()};
        JTextField[] textFields = {hostField.getHostTextField()};
        addLabelsAndTextFields(textLabels, textFields);
    }

    /**
     * Add combo boxes to the panel
     */
    private void addComboBoxes() {
        List<JLabel> comboBoxLabels = new ArrayList<>();
        comboBoxLabels.add(methodField.getMethodLabel());
        comboBoxLabels.add(bodyTypeField.getBodyTypeLabel());
        List<JComboBox<String>> comboBoxes = new ArrayList<>();
        comboBoxes.add(methodField.getMethodComboBox());
        comboBoxes.add(bodyTypeField.getBodyTypeComboBox());
        addLabelComboBoxRows(comboBoxLabels, comboBoxes, this);
    }

    /**
     * Add labels and text fields to the panel
     * @param labels the labels to add
     * @param textFields the text fields to add
     */
    private void addLabelTextRows(JLabel[] labels, JTextField[] textFields, Container container) {
        int numLabels = labels.length;
        addLabelsToContainer(numLabels, labels, textFields, container);
    }

    /**
     * Add labels and text fields to the container
     * @param numLabels the number of labels
     * @param labels the labels to add
     * @param textFields the text fields to add
     * @param container the container to add the labels and text fields to
     */
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

    /**
     * Add labels and combo boxes to the container
     * @param labels the labels to add
     * @param comboBoxes the combo boxes to add
     * @param container the container to add the labels and combo boxes to
     */
    private void addLabelComboBoxRows(List<JLabel> labels, List<JComboBox<String>> comboBoxes, Container container) {
        int numLabels = labels.size();
        for (int currentLabelIndex = FIRST_LABEL_INDEX; currentLabelIndex < numLabels; currentLabelIndex++) {
            labels.get(currentLabelIndex).setLabelFor(comboBoxes.get(currentLabelIndex));
            container.add(labels.get(currentLabelIndex));
            container.add(comboBoxes.get(currentLabelIndex));
        }
    }

    /**
     * Get the host field
     * @return the host field
     */
    public HostField getHostField() {
        return hostField;
    }
    

    /**
     * Get the body type field
     * @return the body type field
     */
    public BodyTypeField getBodyTypeField() {
        return bodyTypeField;
    }

    /**
     * Get the body field
     * @return the body field
     */
    public BodyField getBodyField() {
        return bodyField;
    }

    /**
     * Get the method field
     * @return the method field
     */
    public MethodField getMethodField() {
        return methodField;
    }

    /**
     * Get the add headers button
     * @return the add headers button
     */
    public AddHeadersButton getAddHeadersButton() {
        return addHeadersButton;
    }

    /**
     * Get the headers dialog
     * @return the headers dialog
     */
    public HeadersDialog getHeadersDialog() {
        return headersDialog;
    }

    /**
     * Set the host field
     * @param hostField the host field
     */
    public void setHostField(HostField hostField) {
        this.hostField = hostField;
    }
    

    /**
     * Set the body type field
     * @param bodyTypeField the body type field
     */
    public void setBodyTypeField(BodyTypeField bodyTypeField) {
        this.bodyTypeField = bodyTypeField;
    }

    /**
     * Set the body field
     * @param bodyField the body field
     */
    public void setBodyField(BodyField bodyField) {
        this.bodyField = bodyField;
    }

    /**
     * Set the method field
     * @param methodField the method field
     */
    public void setMethodField(MethodField methodField) {
        this.methodField = methodField;
    }

    /**
     * Set the add headers button
     * @param addHeadersButton the add headers button
     */
    public void setAddHeadersButton(AddHeadersButton addHeadersButton) {
        this.addHeadersButton = addHeadersButton;
    }

    /**
     * Set the headers dialog
     * @param headersDialog the headers dialog
     */
    public void setHeadersDialog(HeadersDialog headersDialog) {
        this.headersDialog = headersDialog;
    }

}
