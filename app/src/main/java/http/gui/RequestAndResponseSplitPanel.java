package http.gui;

import javax.swing.JSplitPane;

public class RequestAndResponseSplitPanel extends JSplitPane{

    public RequestAndResponseSplitPanel() {
        super(JSplitPane.HORIZONTAL_SPLIT, new RequestControlsPanel(), new ResponsePanel());        
        this.setOneTouchExpandable(true);
        this.setResizeWeight(0.5);
        
    }

    public RequestControlsPanel getRequestControlsPanel() {
        return (RequestControlsPanel) this.getLeftComponent();
    }

    public ResponsePanel getResponsePanel() {
        return (ResponsePanel) this.getRightComponent();
    }
}
