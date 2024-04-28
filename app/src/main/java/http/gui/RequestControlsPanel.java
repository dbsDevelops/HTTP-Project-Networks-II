package http.gui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import http.project.networks.ii.GreetClient;
import http.project.networks.ii.Request;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Container;
import java.util.List;
import java.util.ArrayList;

public class RequestControlsPanel extends JPanel {

    private static final int FIRST_LABEL_INDEX = 0;
    private static final int PANEL_WIDTH = 300;
    private static final int PANEL_HEIGHT = 300;

    private transient GreetClient greetClient;
    private transient Request request;

    private transient HostField hostField;
    private transient PortField portField;
    private BodyTypeField bodyTypeField;
    private MethodField methodField;
    private SendRequestButton sendRequestButton;
    private HeadersDialog headersDialog;
    private AddHeadersButton addHeadersButton;
    
    private GridLayout gridLayout = new GridLayout(GuiUtils.ROWS, GuiUtils.COLUMNS);

    public RequestControlsPanel() {
        super();
        this.hostField = new HostField();
        this.portField = new PortField();
        this.bodyTypeField = new BodyTypeField();
        this.methodField = new MethodField();
        this.sendRequestButton = new SendRequestButton(greetClient, request);
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

    public void addButtons() {
        this.add(addHeadersButton);
        this.add(sendRequestButton);
    }

    public void addComponents() {
        addTextFields();
        addComboBoxes();
        addButtons();
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

    public SendRequestButton getSendRequestButton() {
        return sendRequestButton;
    }

    public AddHeadersButton getAddHeadersButton() {
        return addHeadersButton;
    }

    public HeadersDialog getHeadersDialog() {
        return headersDialog;
    }
}
