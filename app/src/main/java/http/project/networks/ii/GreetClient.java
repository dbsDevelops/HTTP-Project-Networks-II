package http.project.networks.ii;
import java.net.*;
import java.io.*;   

public class GreetClient {

    private static final String SERVER_NOT_FOUND = "Server not found: ";
    private static final String IO_ERROR = "I/O error: ";

    private String host;
    private int port;

    private Cookie cookie;

    public GreetClient(int port) {
        this.host = "";
        this.port = port;
        this.cookie = null;
    }

    public void sendRequest(URL url, Request request) {

        this.host = url.getHost();

        try(Socket socket = new Socket(this.host, this.port)){
            // Add timeout for closing the connection 
            socket.setSoTimeout(500); 

            // Handling the client request:
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println(request.toString());

            // Handling the server response:
            InputStream is = socket.getInputStream();
            readResponse(is);
        } catch(UnknownHostException e){
            System.err.println(SERVER_NOT_FOUND + e.getMessage());

        } catch(IOException e){
            System.err.println(IO_ERROR + e.getMessage());
        } 
    }

    public void readResponse(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String responseLine = "";
        responseLine = br.readLine();
        while (responseLine != null){
            System.out.println(responseLine);
            responseLine = br.readLine();
        }
    }
}
