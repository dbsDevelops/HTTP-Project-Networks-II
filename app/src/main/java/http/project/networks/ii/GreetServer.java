package http.project.networks.ii;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;

public class GreetServer {

    private static final int MAXIMUM_NUMBER_OF_REQUEST_PARTS = 2;

    APITeachers apiTeachers;
    Path staticFiles;
    
    //List<Socket> connections;
    public GreetServer(String staticFilesPath) {
        this.apiTeachers = new APITeachers();
        this.apiTeachers.teachersMockData();
        this.staticFiles = Paths.get(staticFilesPath);
        //If the path is not absolute, we convert it into absolute, to avoid problems of relative routes
        if(!this.staticFiles.isAbsolute()) {
            this.staticFiles = this.staticFiles.toAbsolutePath();
        }
        System.out.println(this.staticFiles.toString());
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
        String receivedRequest = new String();
        int requestParts = 0;
        return readRequest(br, receivedRequest, requestParts);
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
        System.err.println(HTTPUtils.CLIENT_CONNECTION_CLOSED);
    }

    protected String readRequest(BufferedReader br, String receivedRequest, int requestParts) throws IOException {
        String requestLine;
        while ((requestLine = br.readLine()) != null) {
            System.out.println(requestLine); // Debugging output
            receivedRequest += requestLine;
            receivedRequest += HTTPUtils.NEW_LINE_CHARACTER;

            // Check for the end of the HTTP headers (blank line)
            if (requestLine.isEmpty()) {
                break;
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
                //System.out.println(filePathString);
                try {
                    HttpRequestBody body = HTTPUtils.createRequestBodyFromFile(filePathString);
                    if(!body.equals(null)) {
                        return new Response(ServerStatusCodes.OK_200.getStatusString(), body);
                    } else {
                        return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "Not found"));
                    }
                    
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return new Response(ServerStatusCodes.INTERNAL_SERVER_ERROR_500.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "Internal Server Error"));
            }
        } else {
            // teapot response, later will be the main page of the server
            return new Response(ServerStatusCodes.IM_A_TEAPOT_418.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "I'm a teapot"));
        }
    }
}

