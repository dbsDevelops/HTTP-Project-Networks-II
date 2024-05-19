package http.project.networks.ii.gui.panels;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import http.project.networks.ii.gui.utils.GuiUtils;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

/**
 * A panel for the response of the request
 */
public class ResponsePanel extends JScrollPane {
    
    public static final int PREFERRED_WIDTH = 800;
    public static final int PREFERRED_HEIGHT = 600;
    public static final int MINIMUM_WIDTH = 300;
    public static final int MINIMUM_HEIGHT = 200;

    JTextArea responseTextArea = new JTextArea();

    /**
     * Create a new ResponsePanel
     */
    public ResponsePanel() {
        super();
        this.setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
        this.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(GuiUtils.RESPONSE_STRING),
                BorderFactory.createEmptyBorder(GuiUtils.BORDER_WIDTH, GuiUtils.BORDER_WIDTH, GuiUtils.BORDER_WIDTH, GuiUtils.BORDER_WIDTH)
        ));
        this.setViewportView(responseTextArea);
        configureTextArea();
    }

    /**
     * Get the text area for the response
     * @return the text area for the response
     */
    public JTextArea getResponseTextArea() {
        return responseTextArea;
    }

    /**
     * Set the text area for the response
     * @param responseTextArea the text area for the response
     */
    public void setResponseTextArea(JTextArea responseTextArea) {
        this.responseTextArea = responseTextArea;
    }

    /**
     * Get the response
     * @return the response
     */
    public void appendResponse(String response) {
        responseTextArea.append(response);
    }

    /**
     * Clear the response
     */
    public void configureTextArea() {
        responseTextArea.setEditable(false);
        responseTextArea.setLineWrap(true);
        responseTextArea.setWrapStyleWord(true);
    }
}
