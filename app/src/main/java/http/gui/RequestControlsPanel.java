package http.gui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Container;

public class RequestControlsPanel extends JPanel {

    private static final int FIRST_LABEL_INDEX = 0;
    private static final int PANEL_WIDTH = 300;
    private static final int PANEL_HEIGHT = 300;

    HostField hostField;
    PortField portField;
    MethodField methodField;
    SendRequestButton sendRequestButton;
    HeadersDialog headersDialog;
    AddHeadersButton addHeadersButton;
    
    GridLayout gridLayout = new GridLayout(GuiUtils.ROWS_AND_COLUMNS, GuiUtils.ROWS_AND_COLUMNS);

    public RequestControlsPanel() {
        super();
        this.hostField = new HostField();
        this.portField = new PortField();
        this.methodField = new MethodField();
        this.sendRequestButton = new SendRequestButton();
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

    public void addMethodField() {
        this.add(methodField);
    }

    public void addButtons() {
        this.add(addHeadersButton);
        this.add(sendRequestButton);
    }

    public void addComponents() {
        JLabel[] labels = {hostField.getHostLabel(), portField.getPortLabel()};
        JTextField[] textFields = {hostField.getHostTextField(), portField.getPortTextField()};
        addLabelsAndTextFields(labels, textFields);
        addMethodField();
        addButtons();
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
