package http.project.networks.ii.gui.panels;

import javax.swing.JSplitPane;

import http.project.networks.ii.gui.buttons.SendRequestButton;

public class RequestAndResponseSplitPanel extends JSplitPane {

    private static final float RESIZE_WEIGHT = 0.5f;

    private SendRequestButton sendRequestButton;

    public RequestAndResponseSplitPanel() {
        super(JSplitPane.HORIZONTAL_SPLIT, new RequestControlsPanel(), new ResponsePanel());
        this.setOneTouchExpandable(true);
        this.setResizeWeight(RESIZE_WEIGHT);
        this.sendRequestButton = new SendRequestButton(this.getRequestControlsPanel().getMethodField(), 
                                                        this.getRequestControlsPanel().getHostField(), 
                                                        this.getRequestControlsPanel().getPortField(), 
                                                        this.getRequestControlsPanel().getBodyTypeField(), 
                                                        this.getRequestControlsPanel().getHeadersDialog(),
                                                        this.getResponsePanel());
        getRequestControlsPanel().add(sendRequestButton);
    }

    public RequestControlsPanel getRequestControlsPanel() {
        return (RequestControlsPanel) this.getLeftComponent();
    }

    public ResponsePanel getResponsePanel() {
        return (ResponsePanel) this.getRightComponent();
    }

    public void setRequestControlsPanel(RequestControlsPanel requestControlsPanel) {
        this.setLeftComponent(requestControlsPanel);
    }

    public void setResponsePanel(ResponsePanel responsePanel) {
        this.setRightComponent(responsePanel);
    }
}
