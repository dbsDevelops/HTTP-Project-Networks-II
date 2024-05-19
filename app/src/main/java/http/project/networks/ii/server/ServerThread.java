package http.project.networks.ii.server;
import java.net.*;
import java.nio.charset.StandardCharsets;

import http.project.networks.ii.requests.Request;
import http.project.networks.ii.utils.HTTPUtils;

import java.io.*;

/**
 * This class is responsible for handling each request individually, to avoid server main thread blocking.
 */
public class ServerThread extends Thread {

    private GreetServer server;
    private Socket clientSocket;
    private boolean keepAlive;

    /**
     * Constructor of the class, it initializes the server and the client socket.
     * @param server The server that is running.
     * @param clientSocket The client socket that is connected to the server.
     */
    public ServerThread(GreetServer server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.keepAlive = true;
    }

    /**
     * Override method that handles each request individually, to avoid server main thread blocking.
     * The method reads each request and handles it, to finally send a response to the client. If a connection is persistent,
     * the connection keeps open until it finds a differen header value of Connection: keep-alive.
     */
    @Override 
    public void run() {
        InputStream clientInputStream = null;
        try {
            clientInputStream = clientSocket.getInputStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientInputStream))){
            while(keepAlive) { //Connection: keep-alive
                String requestString = "";
                if(!clientSocket.isClosed()) {
                    if(server.port == HTTPUtils.HTTPS_PORT) {
                        requestString = HTTPUtils.decryptMessage(server.readBase64String(in).getBytes(StandardCharsets.UTF_8), server.serverHello.symmetricKey);
                    } else {
                        requestString = server.readRequest(in);
                    }
                }
                if (requestString.isEmpty()) {
                    break;
                }
                
                //SEND RESPONSE
                OutputStream clientOutput = clientSocket.getOutputStream();
                Request request = Request.parse(requestString, server.port);

                //System.out.println("Request without parse: \n" + requestString);
                System.out.println("Request: \n" + request.toString());
                keepAlive = request.isConnectionAlive();
                //System.out.println("This is the current keep-alive: " + keepAlive);
                server.response(clientOutput, request);
                if (!keepAlive) {
                    break; // If 'Connection: close' is specified, terminate the loop
                }
            }
            //in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }    
    }
}
