package http.gui;

import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import http.project.networks.ii.GreetClient;
import http.project.networks.ii.HTTPUtils;
import http.project.networks.ii.Request;
import http.project.networks.ii.Verbs;

public class SendRequestButton extends JButton {

    private transient GreetClient greetClient;
    private transient Request request;

    public SendRequestButton(GreetClient greetClient, Request request) {
        super(GuiUtils.SEND_STRING);
        this.greetClient = greetClient;
        this.request = request;
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendRequest();
            }
        });
    }

    public void addVerbToRequest(String verb) {
        request.setMethod(Verbs.GET);
        try {
            request.setUrl(new URL("http://localhost/teachers"));
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        request.setProtocolVersion("HTTP/1.1");
        request.setHeaders(null);
        request.setBody(null);
    }

    public void sendRequest() {
        greetClient.getInstance(HTTPUtils.HTTP_PORT);
        System.out.println(request.toString());
    }
}
