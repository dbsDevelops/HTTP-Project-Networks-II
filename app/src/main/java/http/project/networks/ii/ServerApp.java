package http.project.networks.ii;

import java.io.*;
import java.net.*;

public class ServerApp {

    public static void main(String[] args) throws IOException {
        int port = 8081;
        APITeachers apiTeachers = new APITeachers();
        apiTeachers.teachersMockData();


        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.err.println("Server is running on port: "+port);
            while(true){
                Socket clientSocket = serverSocket.accept();
                System.err.println("Client connected");
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String s;
                String requeststr = "";
                int requestParts = 0;
                while((s = in.readLine())!=null){
                    requeststr += s + "\n";

                    System.out.println(s);

                    if(s.isEmpty()){
                        requestParts++;
                    }

                    if(requestParts >= 2){
                        break;
                    }
                    
                }
                OutputStream clientOutput = clientSocket.getOutputStream();
                Request request = Request.parse(requeststr);

                String response = "";

                String[] urlParts = request.url.getPath().split("/");
                String path = "";
                for (String part : urlParts) {
                    if (part.isEmpty()) {
                    continue;
                    }
                    path += "/" + part;
                }

                if (path.equals("/teachers")) {
                    response = apiTeachers.readRequest(request);
                }
                else {
                    // teapod response
                    response = new Response(418, "I'm a teapot", "I'm a teapot").toString();
                }
                
                System.out.println("Response: \n" + response);

                clientOutput.write(response.getBytes());
            
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
