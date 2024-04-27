package http.gui;

import javax.swing.JButton;

import http.project.networks.ii.GreetClient;
import http.project.networks.ii.Request;

public class SendRequestButton extends JButton {

    private GreetClient greetClient;
    private Request request;

    public SendRequestButton(GreetClient greetClient, Request request) {
        super(GuiUtils.SEND_STRING);
        this.greetClient = greetClient;
        this.request = request;
    }

    
}
