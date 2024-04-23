package http.gui;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridLayout;

public class RequestControlsPanel extends JPanel {

    private static final int PANEL_WIDTH = 300;
    private static final int PANEL_HEIGHT = 300;
    
    GridLayout gridLayout = new GridLayout(GuiUtils.ROWS_AND_COLUMNS, GuiUtils.ROWS_AND_COLUMNS);

    public RequestControlsPanel() {
        super();
        this.setLayout(gridLayout);
        this.setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(GuiUtils.REQUEST_STRING),
                BorderFactory.createEmptyBorder(GuiUtils.BORDER_WIDTH, GuiUtils.BORDER_WIDTH, GuiUtils.BORDER_WIDTH, GuiUtils.BORDER_WIDTH)
        ));
    }

}
