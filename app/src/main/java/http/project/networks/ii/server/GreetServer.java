package http.project.networks.ii.server;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

import http.project.networks.ii.APILogin;
import http.project.networks.ii.APITeachers;
import http.project.networks.ii.Cookie;
import http.project.networks.ii.HTTPUtils;
import http.project.networks.ii.HttpBodyType;
import http.project.networks.ii.HttpRequestBody;
import http.project.networks.ii.HttpRequestHeaders;
import http.project.networks.ii.Request;
import http.project.networks.ii.Response;

public class GreetServer {

    private volatile boolean running = true; // Used to check if server is running and warn rest of the threads

    private APITeachers apiTeachers;
    private APILogin apiLogin;
    private Path staticFiles;
    private ServerSocket serverSocket;
    private List<Cookie> cookies;

    // Singleton pattern
    public GreetServer(String staticFilesPath) {
        this.apiTeachers = new APITeachers();
        this.apiLogin = new APILogin();
        this.apiTeachers.initialiseTeachersMockData();
        this.staticFiles = Paths.get(staticFilesPath);
        //If the path is not absolute, we convert it into absolute, to avoid problems of relative routes
        if(!this.staticFiles.isAbsolute()) {
            this.staticFiles = this.staticFiles.toAbsolutePath();
        }
        System.out.println(this.staticFiles.toString());
        this.cookies = new ArrayList<>();
        for(int i=0; i<3;i++) {
            cookies.add(new Cookie());
        }
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
        return readRequest(br);
    }

    protected void response(OutputStream clientOutput, Request request) throws IOException {
        String urlPath = request.getUrl().getPath();
        Response response = handleUrl(urlPath, request);
        
        handleCookies(request,response);

        // Output the response
        System.out.println(HTTPUtils.RESPONSE + response);

        // Write the output to the client
        clientOutput.write(response.toString().getBytes());
        //If the body has binary content, we sent it directly
        if(response.getBodyContent() == null && response.getBinaryContent() != null) {
            clientOutput.write(response.getBinaryContent());
        }
    
        // Clean the output and close the stream
        clientOutput.flush();
        clientOutput.close();
        //System.err.println(HTTPUtils.CLIENT_CONNECTION_CLOSED);
    }

    protected String readRequest(BufferedReader br) throws IOException {
        StringBuilder requestBuilder = new StringBuilder();
        String line;
        int contentLength = 0;
    
        // Read headers
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            requestBuilder.append(line).append(HTTPUtils.NEW_LINE_CHARACTER);
            // Check for Content-Length header
            if (line.startsWith("Content-Length: ")) {
                contentLength = Integer.parseInt(line.substring(16).trim());
            }
        }
    
        // Read the blank line following the headers
        requestBuilder.append(HTTPUtils.NEW_LINE_CHARACTER);
    
        // If there's content to read, read it
        if (contentLength > 0) {
            char[] body = new char[contentLength];
            int bytesRead = br.read(body, 0, contentLength);
            requestBuilder.append(body, 0, bytesRead);
        }
    
        return requestBuilder.toString();
    }
    

    protected Response handleUrl(String urlPath, Request request) {
        String[] urlParts = urlPath.split(HTTPUtils.SLASH_CHARACTER);
        if (urlParts.length > 1) {
            if(urlParts[1].equals("teachers")){
                return apiTeachers.readRequest(request);
            } 
            else if (urlParts[1].equals("login")){
                return apiLogin.readRequest(request);
            }            
            else {
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

    //Guarantees that the cookies are updated and the client will always have cookies
    public void handleCookies(Request request, Response response) {
        List<Cookie> cookiesToRemove = new ArrayList<>();
        List<Cookie> cookiesToAdd = new ArrayList<>();
        for(Cookie cookie : this.cookies) {
            if(HTTPUtils.isExpiredCookie(cookie)) { //Cookie is expired
                Cookie newCookie = new Cookie();
                cookiesToRemove.add(cookie);
                cookiesToAdd.add(newCookie);
                //response.responseHeaders.headers.remove(HttpHeaders.SET_COOKIE.getHeader() + ": " + cookie.toString());
                response.getResponseHeaders().removeHeader(HttpRequestHeaders.SET_COOKIE, cookie.toString());
                response.getResponseHeaders().addHeaderToHeaders(HttpRequestHeaders.SET_COOKIE, newCookie.toString());        
            } else if(!HTTPUtils.existServerCookie(request, cookie)) { //Cookie is not expired and doesn´t exist in the request
                response.getResponseHeaders().addHeaderToHeaders(HttpRequestHeaders.SET_COOKIE, cookie.toString());
            }
        }

        for(Cookie cookie : cookiesToRemove) {
            this.cookies.remove(cookie);
        }

        for(Cookie cookie : cookiesToAdd) {
            this.cookies.add(cookie);
        }
    }
}

