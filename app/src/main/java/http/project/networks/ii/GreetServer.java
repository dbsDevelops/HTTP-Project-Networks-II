package http.project.networks.ii;
import java.net.*;
import java.io.*;

public class GreetServer {

    private static final int MAXIMUM_NUMBER_OF_REQUEST_PARTS = 2;

    APITeachers apiTeachers;
    
    //List<Socket> connections;
    public GreetServer() {
        this.apiTeachers = new APITeachers();
        this.apiTeachers.teachersMockData();
        //this.connections = new ArrayList<>();
    }

    public void initServer() {

        int port = HttpUtils.HTTP_PORT;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println(HttpUtils.SERVER_IS_RUNNING_ON_PORT + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println(HttpUtils.CLIENT_CONNECTED);
                ServerThread serverThread = new ServerThread(this, clientSocket);
                serverThread.start();
            }
        } catch (IOException e) {
            System.err.println(HttpUtils.COULD_NOT_LISTEN_ON_PORT + port);
            System.exit(-1);
        }

    }

    protected String handleRequest(BufferedReader in) throws IOException {
        String s;
        StringBuilder requeststr = new StringBuilder();
        int requestParts = 0;

        while((s = in.readLine())!=null){
            requeststr.append(s);
            requeststr.append(HttpUtils.NEW_LINE_CHARACTER);

            if(s.isEmpty()){
                requestParts++;
            }

            if(requestParts >= MAXIMUM_NUMBER_OF_REQUEST_PARTS){
                break;
            }
        }
        
        return requeststr.toString();
    }

    protected void response(OutputStream clientOutput, Request request) throws IOException {

        String response = "";

        String[] urlParts = request.url.getPath().split("/");
                
        if (urlParts.length > 1) {
            if(urlParts[1].equals("teachers")){
                response = apiTeachers.readRequest(request);
            }
        } else {
            // teapot response
            response = new Response(ServerStatusCodes.IM_A_TEAPOT_418.getStatusString(), "I'm a teapot").toString();
        }
        
        System.out.println(HttpUtils.RESPONSE + response);

        clientOutput.write(response.getBytes());
    
        clientOutput.flush();
        clientOutput.close();
        System.err.println("Client connection closed!");
    }

    //public String getUrlString
}

