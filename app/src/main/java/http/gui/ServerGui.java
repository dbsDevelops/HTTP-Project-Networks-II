package http.gui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class ServerGui extends JFrame{

    public ServerGui(){
        super("Server");
        setSize(400, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ServerGui();
    }
}
