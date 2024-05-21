package http.project.networks.ii.server;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

import java.security.InvalidKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import http.project.networks.ii.api.teachers_api.APITeachers;
import http.project.networks.ii.cookies.Cookie;
import http.project.networks.ii.headers.ResponseHeaders;
import http.project.networks.ii.logger.Logger;
import http.project.networks.ii.requests.Request;
import http.project.networks.ii.responses.Response;
import http.project.networks.ii.tls.ServerHello;
import http.project.networks.ii.tls.TlsShared;
import http.project.networks.ii.utils.HTTPUtils;
import http.project.networks.ii.utils.HttpBodyType;
import http.project.networks.ii.utils.HttpBody;
import http.project.networks.ii.utils.HttpRequestHeaders;
import http.project.networks.ii.utils.Verbs;

/**
 * This class represents the server that will be listening to the client connections. It will be responsible for 
 * managing the requests and responses that are sent and received, respectively. The server will be running until 
 * an interrupt signal is received or the terminal that runs the program is closed.
 */
public class GreetServer {

    private volatile boolean running = true; // Used to check if server is running and warn rest of the threads

    private APITeachers apiTeachers;
    private Path staticFiles;
    private ServerSocket serverSocket;
    private List<Cookie> cookies;
    ServerHello serverHello;
    private Logger logger;
    int port;

    /**
     * Constructor of the class GreetServer
     * @param staticFilesPath The path where the static files are located.
     * @param port The port where the server will be listening to the client connections.
     */
    public GreetServer(String staticFilesPath, int port) {
        this.apiTeachers = new APITeachers();
        this.apiTeachers.initialiseTeachersMockData();
        this.staticFiles = Paths.get(staticFilesPath);
        //If the path is not absolute, we convert it into absolute, to avoid problems of relative routes
        if(!this.staticFiles.isAbsolute()) {
            this.staticFiles = this.staticFiles.toAbsolutePath();
        }
        System.out.println(this.staticFiles.toString());
        this.cookies = new ArrayList<>();
        this.port = port;
        for(int i=0; i<3;i++) {
            if(this.port == HTTPUtils.HTTPS_PORT) {
                Cookie cookie = new Cookie();
                cookie.setSecure(true);
                cookies.add(cookie);
            } else {
                cookies.add(new Cookie());
            }
        }
        this.logger = new Logger("server_log_");
    }

    /**
     * Method that returns the logger of the server.
     * @return The logger of the server.
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Method that sets the logger of the server.
     * @param logger The logger of the server.
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }   

    /**
     * Function that starts the server to listen to client connections. The server will be running until an interrupt 
     * signal is received or the terminal that runs the program is closed.
     */
    public void initServer() {
        try {
            serverSocket = new ServerSocket(this.port);
            System.out.println(HTTPUtils.SERVER_IS_RUNNING_ON_PORT + this.port);
            logger.log(HTTPUtils.SERVER_IS_RUNNING_ON_PORT + this.port, Logger.INFO);
            while (running) {
                handleClientConnection();
            }
            //logger.close();
        } catch (IOException e) {
            System.err.println("Could not listen on port " + this.port + ": " + e.getMessage());
            logger.log("Could not listen on port " + this.port + ": " + e.getMessage(), Logger.ERROR);
        } 
    }

    /**
     * Method that manages server connections, waiting to receive new connections. When it receives one, it creates a 
     * runnable object called ServerThread that manages the requests, to prevent the server from blocking itself handling 
     * each request.
     */
    public void handleClientConnection() {
        try {
            if(this.port == HTTPUtils.HTTPS_PORT) {
                CertificateFactory factory = CertificateFactory.getInstance("X.509");
                Path path = Paths.get("app", "src", "main", "java", "http", "project", "networks", "ii", "tls", "certif.crt");
                InputStream is = new FileInputStream(path.toFile());
                Certificate certificate = factory.generateCertificate(is);
                serverHello = new ServerHello(this.port, certificate, new TlsShared(), serverSocket.accept());
                serverHello.processClientAndServer();
                is.close();
                serverHello.receivePremasterSecret();
                
                //Cookies are secure now that we are using HTTPS
                for(int i=0; i<3;i++) {
                    this.cookies.get(i).setSecure(true);
                }
            }
            Socket clientSocket = serverSocket.accept();
            clientSocket.setKeepAlive(true);
            System.out.println(HTTPUtils.CLIENT_CONNECTED + " from " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
            logger.log(HTTPUtils.CLIENT_CONNECTED + " from " + clientSocket.getInetAddress() + ":" + clientSocket.getPort(), Logger.INFO);
            ServerThread serverThread = new ServerThread(this, clientSocket);
            serverThread.start();
            
        } catch (IOException e) {// Break the loop if server is supposed to stop
            System.err.println("Error accepting connection: " + e.getMessage());
            logger.log("Error accepting connection: " + e.getMessage(), Logger.ERROR);
        } catch (CertificateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Method that for the execution of the server. This method was created for the purpose of using it within the GUI.
     */
    public void stopServer() {
        running = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing server: " + e.getMessage());
            logger.log("Error closing server: " + e.getMessage(), Logger.ERROR);
        }
    }

    /**
     * Method that is responsible for sending a response to the client based on their request. The method processes 
     * the request to the server, and generates an appropriate response to the client (encrypted or unencrypted 
     * depending on whether TLS is used).
     * @param clientOutput The outputstream that will send the response from the server through the client socket.
     * @param request The request that the server is receiving and has to be processed to generate a response.
     * @throws IOException If there is a write error to the socket, it will be thrown.
     */
    protected void response(OutputStream clientOutput, Request request) throws IOException {
        String urlPath = request.getUrl().getPath();
        Response response = handleUrl(urlPath, request);
        
        handleCookies(request,response);

        // Output the response
        String responseStr = handlePetitionType(request, response);
        System.out.println(HTTPUtils.RESPONSE + responseStr);
        logger.log(HTTPUtils.RESPONSE + responseStr, Logger.INFO);

        // Write the output to the client
        byte[] responseBytes = responseStr.getBytes(StandardCharsets.UTF_8);
        //If the body has no binary content, we sent it directly
        if(response.getBodyContent() == null && response.getBinaryContent() != null
        && !request.getMethod().equals(Verbs.HEAD)) {
            responseBytes = concatByteArrays(responseBytes, response.getBinaryContent());
        }
        clientOutput.write(handleResponse(responseBytes));
    
        // Clean the output and close the stream
        clientOutput.flush();
        clientOutput.close();
    }

    /**
     * Method that takes care of reading an incoming HTTP request from the client. The method will identify whether 
     * this request has a body or not, in order to process it and read it dynamically according to its length, obtained 
     * from the "Content-Lenght" header.
     * @param br The buffered reader coming from the input stream of the socket, from which the request coming from the 
     * client will be read.
     * @return String containing the client's request
     * @throws IOException If there's a read error, this exception will be thrown.
     */
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
        // If there's content to read, read it
        if (contentLength > 0) {
            // Read the blank line following the headers
            requestBuilder.append(HTTPUtils.NEW_LINE_CHARACTER);
            char[] body = new char[contentLength];
            int bytesRead = br.read(body, 0, contentLength);
            requestBuilder.append(body, 0, bytesRead);
        }
    
        return requestBuilder.toString();
    }

    /**
     * Method used to read the base64 string from the client's symmetric key encryption. This method should only 
     * be used to read the request if it is a tls connection.
     * @param reader The buffered reader coming from the input stream of the socket, from which the cyphered request coming from the 
     * client will be read.
     * @return Base64 string encrypted with the client's symmetric key (obtained from the TLS Handshake)
     * @throws IOException If there's a read error, this exception will be thrown
     */
    public String readBase64String(BufferedReader reader) throws IOException {
        return reader.readLine();
    }
    

    /**
     * Method that handles the request and returns the appropriate response. The method will parse the path of 
     * the request that has been included in the url, and then handle it to see if it is a request to the API or 
     * if it is a request for a static resource.
     * @param urlPath The path that has been included in the url for the request to the server.
     * @param request The request sent by the client and received by the server.
     * @return An appropriate response, which has been created from the request made to the server.
     */
    protected Response handleUrl(String urlPath, Request request) {
        String[] urlParts = urlPath.split(HTTPUtils.SLASH_CHARACTER);
        if (urlParts.length > 1) {
            if(urlParts[1].equals("teachers")){
                return apiTeachers.readRequest(request);
            }          
            else {
                try {
                    //If the method is not GET, HEAD or POST, we return a 405 Method Not Allowed
                    if(request.getMethod().equals(Verbs.PUT) || request.getMethod().equals(Verbs.DELETE)) {
                        return new Response(ServerStatusCodes.METHOD_NOT_ALLOWED_405.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.METHOD_NOT_ALLOWED));
                    }
                    String auxPathString = staticFiles.toString() + urlPath;
                    Path auxPath = Paths.get(auxPathString);
                    if (Files.isDirectory(auxPath)) {
                        // Check for the trailing slash and redirect if necessary
                        if (!urlPath.endsWith("/")) {
                            ResponseHeaders headers = new ResponseHeaders();
                            headers.addHeaderToHeaders(HttpRequestHeaders.LOCATION, urlPath + "/");
                            return new Response(ServerStatusCodes.MOVED_PERMANENTLY_301.getStatusString(), new HttpBody(HttpBodyType.RAW, "The resource has been moved permanently"), headers);
                        }
                        // Serve the file if it exists
                        HttpBody htmlBody = HTTPUtils.createRequestBodyFromFile(staticFiles.toString(), urlPath);
                        return new Response(ServerStatusCodes.OK_200.getStatusString(), htmlBody);
                    } else {
                        // Serve the file if it exists
                        HttpBody body = HTTPUtils.createRequestBodyFromFile(staticFiles.toString(), urlPath);
                        if (body != null) {
                            return new Response(ServerStatusCodes.OK_200.getStatusString(), body);
                        } else {
                            return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.NOT_FOUND));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return new Response(ServerStatusCodes.INTERNAL_SERVER_ERROR_500.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.INTERNAL_SERVER_ERROR));
                }
            }
        } else {
            try {
                if(staticFiles.resolve("index.html").toFile().exists()) {
                    HttpBody body = HTTPUtils.createRequestBodyFromFile(staticFiles.toString(), "/index.html");
                    return new Response(ServerStatusCodes.OK_200.getStatusString(), body);
                } else {
                    return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpBody(HttpBodyType.RAW, "This is the DJGI/1.0.0 server main page"));
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Response(ServerStatusCodes.INTERNAL_SERVER_ERROR_500.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * This method is used to handle the cookies. Guarantees that the cookies are updated and the client will always have cookies.
     *  It checks if the cookies are expired or if they are not in the request.
     *  If they are expired, it creates a new cookie with the same id and a new value.
     *  If they are not in the request, it adds them to the response.
     * @param request Request coming to the server from the client.
     * @param response Response to be processed with respect to cookies, and then sent back to the client.
     */
    public void handleCookies(Request request, Response response) {
        List<Cookie> cookiesToRemove = new ArrayList<>();
        List<Cookie> cookiesToAdd = new ArrayList<>();
        for(Cookie cookie : this.cookies) {
            if(HTTPUtils.isExpiredCookie(cookie)) { //Cookie is expired
                Cookie newCookie = new Cookie();
                cookiesToRemove.add(cookie);
                cookiesToAdd.add(newCookie);
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
            if(this.port == HTTPUtils.HTTPS_PORT) {
                cookie.setSecure(true);
            }
        }
    }

    /**
     * Method that determines whether or not the response is to be encrypted with the symmetric key generated by TLS.
     * @param response Array of bytes corresponding to the response to be sent
     * @return The same byte array if TLS is not used, or an encrypted one corresponding to the result of symmetric key 
     * encryption, and its subsequent conversion to base64.
     */
    private byte[] handleResponse(byte[] response) {
        if (this.port == HTTPUtils.HTTPS_PORT) {
            try {
                String encryptedResponse = HTTPUtils.encryptMessage(response, serverHello.symmetricKey);
                return encryptedResponse.getBytes(StandardCharsets.UTF_8);  
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }
    

    private byte[] concatByteArrays(byte[] array1, byte[] array2) {
        byte[] result = new byte[array1.length + array2.length];
    
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
    
        return result;
    }

    /**
     * Method that is in charge of managing whether the request is HEAD or a different one, in order to send 
     * or not the body of the response.
     * @param clientRequest Request from the client
     * @param serverResponse Response of the server 
     * @return A text string that will or will not contain the body of the response, depending if the request is HEAD type or other.
     */
    private String handlePetitionType(Request clientRequest, Response serverResponse) {
        if(clientRequest.getMethod().equals(Verbs.HEAD)) {
            return serverResponse.headTypeResponse();
        } else {
            return serverResponse.toString();
        }
    }
    
}

