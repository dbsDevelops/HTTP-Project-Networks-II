package http.project.networks.ii;
import java.net.*;
import java.io.*;

public class ServerThread extends Thread {

    private GreetServer server;
    private Socket clientSocket;

    public ServerThread(GreetServer server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    @Override 
    public void run() {
        //System.out.println("Runnable");                 
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){
            String requestString = server.handleRequest(in);
            //SEND RESPONSE
            OutputStream clientOutput = clientSocket.getOutputStream();
            Request request = Request.parse(requestString);
            server.response(clientOutput, request);
            //in.close(); 
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }
}
