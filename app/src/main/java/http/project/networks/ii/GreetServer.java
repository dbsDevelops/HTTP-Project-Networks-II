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
        int port = HTTPUtils.HTTP_PORT;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println(HTTPUtils.SERVER_IS_RUNNING_ON_PORT + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println(HTTPUtils.CLIENT_CONNECTED);
                ServerThread serverThread = new ServerThread(this, clientSocket);
                serverThread.start();
            }
        } catch (IOException e) {
            System.err.println(HTTPUtils.COULD_NOT_LISTEN_ON_PORT + port);
            System.exit(-1);
        }
    }

    protected String handleRequest(BufferedReader br) throws IOException {
        StringBuilder receivedRequest = new StringBuilder();
        int requestParts = 0;
        return readRequest(br, receivedRequest, requestParts);
    }

    protected void response(OutputStream clientOutput, Request request) throws IOException {
        String[] urlParts = request.url.getPath().split(HTTPUtils.SLASH_CHARACTER);
        String response = handleUrlParts(urlParts, request);
        
        // Output the response
        System.out.println(HTTPUtils.RESPONSE + response);

        // Write the output to the client
        clientOutput.write(response.getBytes());
    
        // Clean the output and close the stream
        clientOutput.flush();
        clientOutput.close();
        System.err.println(HTTPUtils.CLIENT_CONNECTION_CLOSED);
    }

    protected String readRequest(BufferedReader br, StringBuilder receivedRequest, int requestParts) throws IOException {
        String requestLine;
        while ((requestLine = br.readLine()) != null) {
            receivedRequest.append(requestLine);
            receivedRequest.append(HTTPUtils.NEW_LINE_CHARACTER);

            if (requestLine.isEmpty()) {
                requestParts++;
            }
            if (requestParts >= MAXIMUM_NUMBER_OF_REQUEST_PARTS) {
                break;
            }
        }
        return receivedRequest.toString();
    }

    protected String handleUrlParts(String[] urlParts, Request request) {
        if (urlParts.length > 1) {
            if(urlParts[1].equals("teachers")){
                return apiTeachers.readRequest(request);
            } else {
                return null; //TODO: Handle this case more optimally
            }
        } else {
            // teapot response
            return new Response(ServerStatusCodes.IM_A_TEAPOT_418.getStatusString(), "I'm a teapot").toString();
        }
    }
}

