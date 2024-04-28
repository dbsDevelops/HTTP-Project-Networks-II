package http.gui.panels;

import javax.swing.JSplitPane;

public class RequestAndResponseSplitPanel extends JSplitPane {

    private static final float RESIZE_WEIGHT = 0.5f;

    public RequestAndResponseSplitPanel() {
        super(JSplitPane.HORIZONTAL_SPLIT, new RequestControlsPanel(), new ResponsePanel());
        this.setOneTouchExpandable(true);
        this.setResizeWeight(RESIZE_WEIGHT);
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
