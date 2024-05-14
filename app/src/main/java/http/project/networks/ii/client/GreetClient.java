package http.project.networks.ii.client;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.crypto.SecretKey;

import org.checkerframework.checker.units.qual.s;

import http.project.networks.ii.api.teachers_api.TeachersClass;
import http.project.networks.ii.cookies.Cookie;
import http.project.networks.ii.requests.Request;
import http.project.networks.ii.tls.ClientHello;
import http.project.networks.ii.tls.TlsShared;
import http.project.networks.ii.utils.HTTPUtils;
import http.project.networks.ii.utils.HttpRequestHeaders;
import http.project.networks.ii.logger.Logger;

import java.io.*;   

public class GreetClient {

    private static final String SERVER_NOT_FOUND = "Server not found: ";
    private static final String IO_ERROR = "I/O error: ";
    private static final int RESET_RESPONSE = 0;

    private String host;
    private int port;
    private String clientCookies;
    private StringBuilder responseBuilder;
    private ClientHello clientHello;
    // private Logger logger;
    CachedData cachedData;
    URL url;

    public GreetClient(int port, CachedData cachedData) {
        this.host = "";
        this.port = port;
        this.clientCookies = null;
        this.responseBuilder = new StringBuilder();
        this.cachedData = cachedData;
        //this.logger = new Logger("client");
    }

    public GreetClient(int port) {
        this.host = "";
        this.port = port;
        this.clientCookies = null;
        this.responseBuilder = new StringBuilder();
        this.cachedData = new CachedData();
        //this.logger = new Logger("client");
    }

    /**
     * Method that sends a request to a specified server hostname
     * @param url where you want to send your request
     * @param request the request you want to send to the server
     */
    public void sendRequest(URL url, Request request) {
        this.url = url;
        this.host = url.getHost();
        if(this.port == HTTPUtils.HTTPS_PORT){
            try {
                this.clientHello = new ClientHello(this.host, this.port, new TlsShared());
                clientHello.sendClientHello();
                clientHello.validateCertificateFromServer();
                clientHello.sendPremasterSecret();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try(Socket socket = new Socket(this.host, this.port)){
            // Add timeout for closing the connection 
            socket.setSoTimeout(500); 

            // Handling the client request:
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);

            //Add cookies in the new request (if there are any)
            if(this.clientCookies != null) {
                request.addCookies(this.clientCookies);
            }

            if(port == HTTPUtils.HTTPS_PORT) {
                pw.println(HTTPUtils.encryptMessage(request.toString().getBytes(StandardCharsets.UTF_8), clientHello.symmetricKey));
            } else {
                pw.println(request.toString());
            }

            // Handling the server response:
            InputStream is = socket.getInputStream();
            readResponse(is);
        } catch(UnknownHostException e){
            System.err.println(SERVER_NOT_FOUND + e.getMessage());
            //logger.log(SERVER_NOT_FOUND + e.getMessage());
        } catch(IOException e){
            System.err.println(IO_ERROR + e.getMessage());
            //logger.log(IO_ERROR + e.getMessage());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }

    public void readResponse(InputStream is) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder responseRaw = new StringBuilder();
        String line;
    
        // Read the response
        if(port == HTTPUtils.HTTPS_PORT){
            while ((line = br.readLine()) != null) {
                responseRaw.append(line);
            }
        } else {
            while ((line = br.readLine()) != null) {
                responseRaw.append(line);
                responseRaw.append("\n");
            }
        }
        //Check if TLS is used, if not the response is not cyphered
        String response;
        if(port == HTTPUtils.HTTPS_PORT) {
            response = HTTPUtils.decryptMessage(responseRaw.toString().getBytes(StandardCharsets.UTF_8), clientHello.symmetricKey);
        } else {
            response = responseRaw.toString();
        }
    
        // Process the response
        processResponse(response);
    }
    
    public void processResponse(String decryptedResponse) throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(decryptedResponse));
        Boolean firstCookieField = true;
        String responseLine = "";
        responseBuilder.setLength(RESET_RESPONSE);
        
        responseLine = br.readLine();
        responseBuilder.append(responseLine + "\n");
    
        Boolean bodystarted = false;
        StringBuilder body = new StringBuilder();
    
        if(responseBuilder.toString().contains("HTTP/1.1 304 Not Modified")){
            if (!cachedData.containsKey(url.toString())) {
                System.out.println("Resource has not been cached before");
            }
            else {
                String data = cachedData.getData(url.toString());
                System.out.println("Data from cache: \n" + data + "\n");
            }
        }
    
        while (responseLine != null) {
            if(bodystarted) {
                body.append(responseLine);
            }
    
            if (responseLine.isEmpty()) {
                bodystarted = true;
            }
    
            System.out.println(responseLine);
            responseLine = br.readLine();
            if (responseLine != null) {
                responseBuilder.append(responseLine + "\n");
                if(isCookieField(responseLine)) {
                    if(firstCookieField) {
                        this.clientCookies = HttpRequestHeaders.COOKIE.getHeader() + ": ";
                        firstCookieField = false;
                    }
                    addServerCookiesToClient(responseLine);
                }
            }
        }
        
        if(bodystarted){
            if (cachedData.containsKey(url.toString())) 
                cachedData.removeData(url.toString());
            cachedData.addData(url.toString(), body.toString());
        }
    
        if(this.clientCookies != null) {
            this.clientCookies = this.clientCookies.substring(0, this.clientCookies.length()-2);
            System.out.println(this.clientCookies + "\n\n\n--------------------------------------------\n\n");
            responseBuilder.append(this.clientCookies);
        }
    }
    

    public String getResponseString() {
        return this.responseBuilder.toString();
    }

    private boolean isCookieField(String field) {
        if(field != null) {
            return field.startsWith(HttpRequestHeaders.SET_COOKIE.getHeader());
        }
        return false;
    }

    private void addServerCookiesToClient(String cookieLine) {
        String cookieValue = cookieLine.substring(HttpRequestHeaders.SET_COOKIE.getHeader().length()+2); //Remove the "Set-Cookie: "
        StringBuilder cookiesValue = new StringBuilder();
        Cookie cookie = Cookie.parse(cookieValue);
        cookiesValue.append(cookie.getName()+"="+cookie.getValue()+"; "); //Add the cookie to the client
        this.clientCookies += cookiesValue.toString();
    }

}
