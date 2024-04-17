package http.project.networks.ii;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;

public class GreetServer {

    private volatile boolean running = true; // Used to check if server is running and warn rest of the threads

    private APITeachers apiTeachers;
    private Path staticFiles;
    private ServerSocket serverSocket;

    public GreetServer(String staticFilesPath) {
        this.apiTeachers = new APITeachers();
        this.apiTeachers.initialiseTeachersMockData();
        this.staticFiles = Paths.get(staticFilesPath);
        //If the path is not absolute, we convert it into absolute, to avoid problems of relative routes
        if(!this.staticFiles.isAbsolute()) {
            this.staticFiles = this.staticFiles.toAbsolutePath();
        }
        System.out.println(this.staticFiles.toString());
    }

    public void initServer(int port) {
        // try (ServerSocket serverSocket = new ServerSocket(port)) {
        //     System.out.println(HTTPUtils.SERVER_IS_RUNNING_ON_PORT + port);
        //     while (running) {
        //         Socket clientSocket = serverSocket.accept();
        //         System.out.println(HTTPUtils.CLIENT_CONNECTED);
        //         ServerThread serverThread = new ServerThread(this, clientSocket);
        //         serverThread.start();
        //     }
        // } catch (IOException e) {
        //     if (!running) break; // Break the loop if server is supposed to stop
        //     System.err.println(HTTPUtils.COULD_NOT_LISTEN_ON_PORT + port);
        //     System.exit(-1);
        // }
        try {
            serverSocket = new ServerSocket(port);
            System.out.println(HTTPUtils.SERVER_IS_RUNNING_ON_PORT + port);
            while (running) {
                handleClientConnection();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + port + ": " + e.getMessage());
        } 
    }

    public void handleClientConnection() {
        try {
            Socket clientSocket = serverSocket.accept();
            clientSocket.setKeepAlive(true);
            System.out.println(HTTPUtils.CLIENT_CONNECTED + " from " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
            ServerThread serverThread = new ServerThread(this, clientSocket);
            serverThread.start();
            
        } catch (IOException e) {// Break the loop if server is supposed to stop
            System.err.println("Error accepting connection: " + e.getMessage());
        }
    }

    public void stopServer() {
        running = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing server: " + e.getMessage());
        }
    }

    protected String handleRequest(BufferedReader br) throws IOException {
        StringBuilder receivedRequest = new StringBuilder();
        return readRequest(br, receivedRequest);
    }

    protected void response(OutputStream clientOutput, Request request) throws IOException {
        String urlPath = request.url.getPath();
        Response response = handleUrl(urlPath, request);
        
        // Output the response
        System.out.println(HTTPUtils.RESPONSE + response);

        // Write the output to the client
        clientOutput.write(response.toString().getBytes());
        //If the body has binary content, we sent it directly
        if(response.getStringContent() == null && response.getBinaryContent() != null) {
            clientOutput.write(response.getBinaryContent());
        }
    
        // Clean the output and close the stream
        clientOutput.flush();
        clientOutput.close();
        //System.err.println(HTTPUtils.CLIENT_CONNECTION_CLOSED);
    }

    protected String readRequest(BufferedReader br, StringBuilder receivedRequest) throws IOException {
        String requestLine;
        boolean isBody = false;
        while ((requestLine = br.readLine()) != null) {
            System.out.println(requestLine); // Debugging output
            receivedRequest.append(requestLine);
            receivedRequest.append(HTTPUtils.NEW_LINE_CHARACTER);

            // Check for the end of the HTTP headers (blank line)
            if (requestLine.isEmpty()) {
                if (isBody) {
                    isBody = false;
                    break;
                }
                isBody = true; // Exit if the request is empty or null
            }
        }
        return receivedRequest.toString();
    }

    protected Response handleUrl(String urlPath, Request request) {
        String[] urlParts = urlPath.split(HTTPUtils.SLASH_CHARACTER);
        if (urlParts.length > 1) {
            if(urlParts[1].equals("teachers")){
                return apiTeachers.readRequest(request);
            } else {
                String filePathString = staticFiles.toString() + urlPath;
                try {
                    HttpRequestBody body = HTTPUtils.createRequestBodyFromFile(filePathString);
                    if(body != null) {
                        return new Response(ServerStatusCodes.OK_200.getStatusString(), body);
                    } else {
                        return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.NOT_FOUND));
                    }
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return new Response(ServerStatusCodes.INTERNAL_SERVER_ERROR_500.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.INTERNAL_SERVER_ERROR));
            }
        } else {
            // teapot response, later will be the main page of the server
            return new Response(ServerStatusCodes.IM_A_TEAPOT_418.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, ServerStatusCodes.IM_A_TEAPOT_418.getMessageString()));
        }
    }
}

