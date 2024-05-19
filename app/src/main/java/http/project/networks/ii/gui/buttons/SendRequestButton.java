package http.project.networks.ii.gui.buttons;

import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import http.project.networks.ii.gui.dialogs.HeadersDialog;
import http.project.networks.ii.gui.fields.BodyTypeField;
import http.project.networks.ii.gui.fields.HostField;
import http.project.networks.ii.gui.fields.MethodField;
import http.project.networks.ii.gui.fields.PortField;
import http.project.networks.ii.gui.panels.ResponsePanel;
import http.project.networks.ii.gui.utils.GuiUtils;
import http.project.networks.ii.requests.Request;
import http.project.networks.ii.utils.HttpBodyType;
import http.project.networks.ii.utils.Verbs;
import http.project.networks.ii.client.GreetClient;

/**
 * A button to send a request from the HTTP client
 */
public class SendRequestButton extends JButton {

    private transient MethodField methodField;
    private transient HostField hostField;
    private transient PortField portField;
    private transient BodyTypeField bodyTypeField;
    private HeadersDialog headersDialog;
    private ResponsePanel responsePanel;
    

    /**
     * Create a new SendRequestButton
     * @param methodField field for selecting the method
     * @param hostField field for entering the host
     * @param portField field for entering the port
     * @param bodyTypeField field for selecting the body type
     * @param headersDialog dialog for entering headers
     * @param responsePanel panel for displaying the response
     */
    public SendRequestButton(MethodField methodField, HostField hostField, PortField portField, BodyTypeField bodyTypeField, HeadersDialog headersDialog, ResponsePanel responsePanel) {
        super(GuiUtils.SEND_STRING);
        this.methodField = methodField;
        this.hostField = hostField;
        this.portField = portField;
        this.bodyTypeField = bodyTypeField;
        this.headersDialog = headersDialog;
        this.responsePanel = responsePanel;
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendRequest();
            }
        });
    }

    /**
     * Method which sends the request once the SendRequestButton is clicked
     */
    public void sendRequest() {
        try {
            GreetClient greetClient = new GreetClient();
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
            //System.out.println(request.toString());
            responsePanel.appendResponse(request.toString());
            greetClient.sendRequest(url, request);
            responsePanel.appendResponse(greetClient.getResponseString());
        } catch (Exception ex) {
            System.out.println("Error sending request: " + ex.getMessage());
        }
    }
}
