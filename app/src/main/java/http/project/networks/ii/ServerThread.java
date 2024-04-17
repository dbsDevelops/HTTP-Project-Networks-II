package http.project.networks.ii;
import java.net.*;
import java.io.*;

public class ServerThread extends Thread {

    private GreetServer server;
    private Socket clientSocket;
    private boolean keepAlive;
    private boolean isBody;

    public ServerThread(GreetServer server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.keepAlive = true;
        this.isBody = false;
    }

    @Override 
    public void run() {      
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){
            while(keepAlive) { //Connection: keep-alive
                String requestString = "";
                if(!clientSocket.isClosed()) {
                    requestString = server.handleRequest(in);
                }
                if (requestString.isEmpty()) {
                    if (isBody) {
                        isBody = false;
                        break;
                    }
                    this.isBody = true; // Exit if the request is empty or null
                } 
                //SEND RESPONSE
                OutputStream clientOutput = clientSocket.getOutputStream();
                Request request = Request.parse(requestString);
                keepAlive = request.isConnectionAlive();
                System.out.println("This is the current keep-alive: " + keepAlive);
                server.response(clientOutput, request);
                if (!keepAlive) {
                    break; // If 'Connection: close' is specified, terminate the loop
                }
            }
            //in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }
}
