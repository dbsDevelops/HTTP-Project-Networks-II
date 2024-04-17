package http.gui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class ClientGui extends JFrame{
    public ClientGui(){
        super("Client");
        setSize(400, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ClientGui();
    }
    
}
