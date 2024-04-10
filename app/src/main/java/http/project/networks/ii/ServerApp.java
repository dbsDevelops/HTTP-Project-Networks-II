package http.project.networks.ii;

import java.io.*;
import java.net.*;

public class ServerApp {

    public static void main(String[] args) throws IOException {
        int port = 8081;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.err.println("Server is running on port: "+port);
            while(true){
                Socket clientSocket = serverSocket.accept();
                System.err.println("Client connected");
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String s;
                while((s = in.readLine())!=null){
                    System.out.println(s);
                    if(s.isEmpty()){
                        break;
                    }
                }
                OutputStream clientOutput = clientSocket.getOutputStream();
                clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
                clientOutput.write("\r\n".getBytes());
                clientOutput.write("<b>Welcome To Asim Code!</b>".getBytes());
                clientOutput.write("\r\n\r\n".getBytes());
                clientOutput.flush();
                System.err.println("Client connection closed!");
                in.close();
                clientOutput.close();        
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + port);
            System.exit(-1);
        }
    }

}