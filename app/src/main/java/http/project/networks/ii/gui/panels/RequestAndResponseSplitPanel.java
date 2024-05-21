package http.project.networks.ii.gui.panels;

import javax.swing.JSplitPane;

import http.project.networks.ii.gui.buttons.SendRequestButton;

/**
 * A split panel for the request controls and the response
 */
public class RequestAndResponseSplitPanel extends JSplitPane {

    private static final float RESIZE_WEIGHT = 0.5f;

    private SendRequestButton sendRequestButton;

    /**
     * Create a new RequestAndResponseSplitPanel
     */
    public RequestAndResponseSplitPanel() {
        super(JSplitPane.HORIZONTAL_SPLIT, new RequestControlsPanel(), new ResponsePanel());
        this.setOneTouchExpandable(true);
        this.setResizeWeight(RESIZE_WEIGHT);
        this.sendRequestButton = new SendRequestButton(this.getRequestControlsPanel().getMethodField(), 
                                                        this.getRequestControlsPanel().getHostField(), 
                                                        this.getRequestControlsPanel().getBodyTypeField(),
                                                        this.getRequestControlsPanel().getBodyField(), 
                                                        this.getRequestControlsPanel().getHeadersDialog(),
                                                        this.getResponsePanel());
        getRequestControlsPanel().add(sendRequestButton);
    }

    /**
     * Get the request controls panel
     * @return the request controls panel
     */
    public RequestControlsPanel getRequestControlsPanel() {
        return (RequestControlsPanel) this.getLeftComponent();
    }

    /**
     * Get the response panel
     * @return the response panel
     */
    public ResponsePanel getResponsePanel() {
        return (ResponsePanel) this.getRightComponent();
    }

    /**
     * Set the request controls panel
     * @param requestControlsPanel the request controls panel
     */
    public void setRequestControlsPanel(RequestControlsPanel requestControlsPanel) {
        this.setLeftComponent(requestControlsPanel);
    }

    /**
     * Set the response panel
     * @param responsePanel the response panel
     */
    public void setResponsePanel(ResponsePanel responsePanel) {
        this.setRightComponent(responsePanel);
    }
}
