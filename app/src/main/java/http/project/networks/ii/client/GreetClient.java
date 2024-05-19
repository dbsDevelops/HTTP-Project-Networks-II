package http.project.networks.ii.client;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import http.project.networks.ii.cookies.Cookie;
import http.project.networks.ii.requests.Request;
import http.project.networks.ii.responses.Response;
import http.project.networks.ii.tls.ClientHello;
import http.project.networks.ii.tls.TlsShared;
import http.project.networks.ii.utils.HTTPUtils;
import http.project.networks.ii.utils.HttpRequestHeaders;

public class GreetClient {

    private static final String SERVER_NOT_FOUND = "Server not found: ";
    private static final String IO_ERROR = "I/O error: ";
    private static final int RESET_RESPONSE = 0;

    private String host;
    private int port;
    private String clientCookies;
    private StringBuilder response;
    private ClientHello clientHello;
    // private Logger logger;
    CachedData cachedData;
    URL url;

    public GreetClient(CachedData cachedData) {
        this.host = "";
        this.clientCookies = null;
        this.response = new StringBuilder();
        this.cachedData = cachedData;
        //this.logger = new Logger("client");
    }

    public GreetClient() {
        this.host = "";
        this.clientCookies = null;
        this.response = new StringBuilder();
        this.cachedData = new CachedData();
        //this.logger = new Logger("client");
    }

    /**
     * Method that is responsible for sending a request and reading and handling a response that has been sent from the 
     * server. The method is able to identify whether or not the request needs to be encrypted and the response decrypted, 
     * depending on whether it is a TLS request or not. 
     * If the selected operation port is TLS, it will initiate the negotiation with the TLS handshake server, in order to 
     * form a symmetric key.
     * @param url URL where you want to send the request
     * @param request Request to be sent to the server
     */
    public void sendRequest(URL url, Request request) {
        this.url = url;
        this.host = url.getHost();
        this.port = HTTPUtils.selectPortBasedOnProtocol(url);
        if (this.port == 443) {
            try {
                this.clientHello = new ClientHello(this.host, this.port, new TlsShared());
                clientHello.sendClientHello();
                clientHello.validateCertificateFromServer();
                clientHello.sendPremasterSecret();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try (Socket socket = new Socket(this.host, this.port)) {
            socket.setSoTimeout(5000);  // Set a timeout for the socket
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);

            if (this.clientCookies != null) {
                request.addCookies(this.clientCookies);
            }

            if (port == HTTPUtils.HTTPS_PORT) {
                pw.println(HTTPUtils.encryptMessage(request.toString().getBytes(StandardCharsets.UTF_8), clientHello.symmetricKey));
            } else {
                pw.println(request.toString());
            }

            InputStream is = socket.getInputStream();
            readAndProcessResponse(is, this.port == HTTPUtils.HTTPS_PORT);
        } catch (UnknownHostException e) {
            System.err.println(SERVER_NOT_FOUND + e.getMessage());
        } catch (IOException e) {
            System.err.println(IO_ERROR + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that is in charge of reading and processing the response from the server, in order to cache the body data and 
     * manage the cookies that the client has to use in future requests, if the same instance of the class persists.
     * @param is The input stream coming from the client socket, to read the response coming from the server.
     * @param isTls To indicate whether TLS is being used or not, to know if it has to decrypt and read the request in one 
     * way (multiple lines) or another (base64 encoded line that is encrypted with the symmetric key of the server).
     * @throws Exception If an error occurs, it will throw exceptions (either read or decrypt).
     */
    private void readAndProcessResponse(InputStream is, boolean isTls) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder responseRaw = new StringBuilder();
        String line;

        if (isTls) {
            // Read single encrypted line and decrypt
            line = br.readLine();
            if (line != null) {
                String decryptedResponse = HTTPUtils.decryptMessage(line.getBytes(StandardCharsets.UTF_8), clientHello.symmetricKey);
                responseRaw.append(decryptedResponse);
            }
        } else {
            while ((line = br.readLine()) != null || br.ready()) {
                responseRaw.append(line).append("\n");
                if (!br.ready()) {
                    break;
                }
            }
        }

        System.out.println(responseRaw.toString());
        processResponse(responseRaw.toString());
    }

    /**
     * Method that is in charge of walking the server response and managing the caching of data and if the client 
     * has to use the cookies coming from the server.
     * @param responseStr The text string corresponding to the response coming from the server
     * @throws IOException If there is an error in the read and walk response, this exception will be thrown.
     */
    private void processResponse(String responseStr) throws IOException {
        Response response = Response.parse(responseStr, this.port);
        this.response.setLength(RESET_RESPONSE);
        this.response.append(response.toString());

        if (response.getStatusCodeAndDescription().contains("304 Not Modified")) {
            if (!cachedData.containsKey(url.toString())) {
                System.out.println("Resource has not been cached before");
            } else {
                String data = cachedData.getData(url.toString());
                System.out.println("Data from cache: \n" + data + "\n");
            }
        }

        boolean firstCookieField = true;
        for (String header : response.getResponseHeaders().getHeaders()) {
            if (isCookieField(header)) {
                if (firstCookieField) {
                    this.clientCookies = HttpRequestHeaders.COOKIE.getHeader() + ": ";
                    firstCookieField = false;
                }
                addServerCookiesToClient(header);
            }
        }

        if (response.getBodyContent() != null) {
            String bodyContent = response.getBodyContent();
            if (cachedData.containsKey(url.toString())) {
                cachedData.removeData(url.toString());
            }
            cachedData.addData(url.toString(), bodyContent);
        }

        if (this.clientCookies != null) {
            this.clientCookies = this.clientCookies.substring(0, this.clientCookies.length() - 2);
            System.out.println(this.clientCookies + "\n\n\n--------------------------------------------\n\n");
            this.response.append(this.clientCookies);
        }
    }

    /**
     * Method that determines whether the header is a set cookie header or not.
     * @param field Line corresponding to the header
     * @return True if it is a cookie-setting header, false otherwise.
     */
    private boolean isCookieField(String field) {
        if (field != null) {
            return field.startsWith(HttpRequestHeaders.SET_COOKIE.getHeader());
        }
        return false;
    }

    /**
     * Method that takes care of adding the cookies sent by the server to be used by the client in future requests.
     * @param cookieLine Cookie setting line
     */
    private void addServerCookiesToClient(String cookieLine) {
        String cookieValue = cookieLine.substring(HttpRequestHeaders.SET_COOKIE.getHeader().length() + 2);
        StringBuilder cookiesValue = new StringBuilder();
        Cookie cookie = Cookie.parse(cookieValue);
        cookiesValue.append(cookie.getName()).append("=").append(cookie.getValue()).append("; ");
        this.clientCookies += cookiesValue.toString();
    }

    public String getResponseString() {
        return this.response.toString();
    }
}
