package http.project.networks.ii.server;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

import javax.crypto.SecretKey;

import java.security.InvalidKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import http.project.networks.ii.api.login_api.APILogin;
import http.project.networks.ii.api.teachers_api.APITeachers;
import http.project.networks.ii.cookies.Cookie;
import http.project.networks.ii.logger.Logger;
import http.project.networks.ii.requests.Request;
import http.project.networks.ii.responses.Response;
import http.project.networks.ii.tls.ServerHello;
import http.project.networks.ii.tls.TlsShared;
import http.project.networks.ii.utils.HTTPUtils;
import http.project.networks.ii.utils.HttpBodyType;
import http.project.networks.ii.utils.HttpRequestBody;
import http.project.networks.ii.utils.HttpRequestHeaders;

public class GreetServer {

    private volatile boolean running = true; // Used to check if server is running and warn rest of the threads

    private APITeachers apiTeachers;
    private APILogin apiLogin;
    private Path staticFiles;
    private ServerSocket serverSocket;
    private List<Cookie> cookies;
    ServerHello serverHello;
    private Logger logger;
    int port;

    // Singleton pattern
    public GreetServer(String staticFilesPath, int port) {
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
        this.logger = new Logger("server_log_");
        this.port = port;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }   

    public void initServer() {
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

    public void handleClientConnection() {
        try {
            if(this.port == 443) {
                CertificateFactory factory = CertificateFactory.getInstance("X.509");
                Path path = Paths.get("app", "src", "main", "java", "http", "project", "networks", "ii", "tls", "certif.crt");
                InputStream is = new FileInputStream(path.toFile());
                Certificate certificate = factory.generateCertificate(is);
                serverHello = new ServerHello(this.port, certificate, new TlsShared(), serverSocket.accept());
                serverHello.processClientAndServer();
                is.close();
                serverHello.receivePremasterSecret();
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

    protected String handleRequest(BufferedReader br) throws IOException {
        return readRequest(br);
    }

    protected void response(OutputStream clientOutput, Request request) throws IOException {
        String urlPath = request.getUrl().getPath();
        Response response = handleUrl(urlPath, request);
        
        handleCookies(request,response);

        // Output the response
        System.out.println(HTTPUtils.RESPONSE + response);
        logger.log(HTTPUtils.RESPONSE + response, Logger.INFO);

        // Write the output to the client
        clientOutput.write(response.toString().getBytes());
        //If the body has binary content, we sent it directly
        if(response.getBodyContent() == null && response.getBinaryContent() != null) {
            clientOutput.write(response.getBinaryContent());
        }
    
        // Clean the output and close the stream
        clientOutput.flush();
        clientOutput.close();
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

    public String readBase64String(BufferedReader reader) throws IOException {
        return reader.readLine();
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
                try {
                    HttpRequestBody body = HTTPUtils.createRequestBodyFromFile(staticFiles.toString(), urlPath);
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
            try {
                if(staticFiles.resolve("index.html").toFile().exists()) {
                    HttpRequestBody body = HTTPUtils.createRequestBodyFromFile(staticFiles.toString(), "/index.html");
                    return new Response(ServerStatusCodes.OK_200.getStatusString(), body);
                } else {
                    //TODO generate default server html
                    return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "This is the DJGI/1.0.0 server main page"));
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Response(ServerStatusCodes.INTERNAL_SERVER_ERROR_500.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.INTERNAL_SERVER_ERROR));
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

