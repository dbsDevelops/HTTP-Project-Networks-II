package http.project.networks.ii;
import java.net.*;
import java.io.*;

public class ServerThread extends Thread {

    private GreetServer server;
    private Socket clientSocket;
    private boolean keepAlive;
    private Cookie cookie;
    private boolean firstTimeCookie;

    public ServerThread(GreetServer server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.keepAlive = true;
        this.cookie = new Cookie(); //Server adds a cookie to the client
        this.firstTimeCookie = true;
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
                    break;
                } 
                //SEND RESPONSE
                OutputStream clientOutput = clientSocket.getOutputStream();
                Request request = Request.parse(requestString);

                if(this.cookie.buildCookie() == null || firstTimeCookie){
                    request.headers.addCookie(new Cookie()); //Cookie is expired
                    if(firstTimeCookie) {
                        firstTimeCookie = false;
                    }
                }

                System.out.println("Request without parse: \n" + requestString);
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
