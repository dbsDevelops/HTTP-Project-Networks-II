package http.gui.buttons;

import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import http.gui.GuiUtils;
import http.gui.dialogs.HeadersDialog;
import http.gui.fields.BodyTypeField;
import http.gui.fields.HostField;
import http.gui.fields.MethodField;
import http.gui.fields.PortField;
import http.project.networks.ii.GreetClient;
import http.project.networks.ii.Request;
import http.project.networks.ii.Verbs;
import http.project.networks.ii.HttpBodyType;

public class SendRequestButton extends JButton {

    private transient MethodField methodField;
    private transient HostField hostField;
    private transient PortField portField;
    private HeadersDialog headersDialog;
    private transient BodyTypeField bodyTypeField;

    public SendRequestButton(MethodField methodField, HostField hostField, PortField portField, BodyTypeField bodyTypeField, HeadersDialog headersDialog) {
        super(GuiUtils.SEND_STRING);
        this.methodField = methodField;
        this.hostField = hostField;
        this.portField = portField;
        this.bodyTypeField = bodyTypeField;
        this.headersDialog = headersDialog;
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendRequest();
            }
        });
    }

    public void sendRequest() {
        try {
            GreetClient greetClient = new GreetClient(portField.getPort());
            System.out.println("Port: " + portField.getPort());
            URL url = new URL(hostField.getHost());
            System.out.println("Host: " + hostField.getHost());
            Verbs method = methodField.getSelectedMethod();
            System.out.println("Method: " + method.toString());
            String protocolVersion = "HTTP/1.1";
            HttpBodyType bodyType = bodyTypeField.getSelectedBodyType();
            System.out.println("Body type: " + bodyType.toString());
            String bodyContent = ""; // Modify this line to get the body content from the bodyField
            System.out.println("Headers:\n" + this.headersDialog.getHeaders().toString());
            Request request = new Request(method, url, protocolVersion, this.headersDialog.getHeaders(), bodyType, bodyContent);
            System.out.println(request.toString());
            greetClient.sendRequest(url, request);
        } catch (Exception ex) {
            System.out.println("Error sending request: " + ex.getMessage());
        }
    }
}
