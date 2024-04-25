package http.gui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

public class ClientGui extends JFrame {

    RequestAndResponseSplitPanel requestAndResponseSplitPanel;

    public ClientGui(){
        // Create and set up the window
        super(GuiUtils.CLIENT_GUI_TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Add content to the window
        initComponenets();

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
    
    public void initComponenets() {
        requestAndResponseSplitPanel = new RequestAndResponseSplitPanel();
        this.add(requestAndResponseSplitPanel);
    }
}
