package http.project.networks.ii;
import java.net.*;
import java.io.*;   

public class GreetClient {

    private String host;
    private int port;

    public GreetClient(int port) {
        this.host = "";
        this.port = port;
    }

    public void sendRequest(URL url, Request request) {

        this.host = url.getHost();
        //int puerto = 80; 

        try(Socket socket = new Socket(this.host, this.port)){

            socket.setSoTimeout(5000); //TODO: manage this through our GreetServer class

            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            // DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            // out.writeUTF(request.toString());
            pw.println(request.toString());

            // Handling the server response:
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String linea;
            linea = br.readLine();
            while (linea != null){
                System.out.println(linea);
                linea = br.readLine();
            }
        } catch(UnknownHostException e){
            System.err.println("Server not found: " + e.getMessage());

        } catch(IOException e){
            System.err.println("I/O error: " + e.getMessage());
        } 
    }
}
