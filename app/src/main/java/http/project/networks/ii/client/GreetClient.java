package http.project.networks.ii.client;
import java.net.*;

import http.project.networks.ii.cookies.Cookie;
import http.project.networks.ii.requests.Request;
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
    private StringBuilder response;
    // private Logger logger;

    public GreetClient(int port) {
        this.host = "";
        this.port = port;
        this.clientCookies = null;
        this.response = new StringBuilder();
        //this.logger = new Logger("client");
    }

    public void sendRequest(URL url, Request request) {

        this.host = url.getHost();

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

            pw.println(request.toString());

            // Handling the server response:
            InputStream is = socket.getInputStream();
            readResponse(is);
        } catch(UnknownHostException e){
            System.err.println(SERVER_NOT_FOUND + e.getMessage());
            //logger.log(SERVER_NOT_FOUND + e.getMessage());
        } catch(IOException e){
            System.err.println(IO_ERROR + e.getMessage());
            //logger.log(IO_ERROR + e.getMessage());
        } 
    }

    public void readResponse(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        Boolean firstCookieField = true;
        String responseLine = "";
        // Reset the response
        response.setLength(RESET_RESPONSE);
        
        responseLine = br.readLine();
        response.append(responseLine + "\n");

        while (responseLine != null) {

            if (responseLine.contains("HTTP/1.1 304 Not Modified")) {
                System.out.println("Resource has not been modified and we have seen it");
                continue;
            }

            System.out.println(responseLine);
            //logger.log(responseLine);
            responseLine = br.readLine();
            response.append(responseLine + "\n");
            if(isCookieField(responseLine)) {
                if(firstCookieField) {
                    this.clientCookies = HttpRequestHeaders.COOKIE.getHeader() + ": "; //Initialize the client cookies
                    firstCookieField = false;
                }
                addServerCookiesToClient(responseLine); //Put all cookies sent by the server in the client
            }
        }

        this.clientCookies = this.clientCookies.substring(0, this.clientCookies.length()-2); //Remove the last "; "
        System.out.println(this.clientCookies);
        //logger.log(this.clientCookies);
        response.append(this.clientCookies);
    }

    public String getResponseString() {
        return this.response.toString();
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
