package http.project.networks.ii.gui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import http.project.networks.ii.gui.panels.RequestAndResponseSplitPanel;
import http.project.networks.ii.gui.utils.GuiUtils;

import javax.swing.SwingUtilities;

public class ClientGui extends JFrame {

    RequestAndResponseSplitPanel requestAndResponseSplitPanel;

    public ClientGui(){
        // Create and set up the window
        super(GuiUtils.CLIENT_GUI_TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Add content to the window
        initComponents();

        // Display the window
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
		        new ClientGui();
            }
        });
    }
    
    public void initComponents() {
        requestAndResponseSplitPanel = new RequestAndResponseSplitPanel();
        this.add(requestAndResponseSplitPanel);
    }
}
