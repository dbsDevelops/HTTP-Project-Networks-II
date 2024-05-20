package http.project.networks.ii.requests;

import java.net.MalformedURLException;
import java.net.URL;

import http.project.networks.ii.headers.RequestHeaders;
import http.project.networks.ii.utils.HTTPUtils;
import http.project.networks.ii.utils.HttpBodyType;
import http.project.networks.ii.utils.HttpBody;
import http.project.networks.ii.utils.HttpRequestHeaders;
import http.project.networks.ii.utils.Verbs;

/**
 * This class represents a HTTP request. It contains the method, the URL, the protocol version, the headers and the body of the request.
 */
public class Request {
    public static final String END_LINE = "\r\n";
    public static final String WHITE_SPACE = " ";
    protected Verbs method;
    protected URL url;
    protected String protocolVersion;
    protected RequestHeaders headers; 
    protected HttpBody body; 

    /**
     * Constructor of the class Request.
     * @param method The method of the request.
     * @param url The URL of the request.
     * @param protocolVersion The protocol version of the request.
     * @param headers The headers of the request.
     * @param bodyType The type of the body of the request.
     * @param bodyContent The content of the body of the request.
     */
    public Request(Verbs method,URL url, String protocolVersion , RequestHeaders headers, HttpBodyType bodyType, String bodyContent) {
        this.method = method;
        this.url = url;
        this.protocolVersion = protocolVersion;
        this.headers = headers;
        if(this.method == Verbs.GET || this.method == Verbs.HEAD) {
            this.body = new HttpBody(HttpBodyType.RAW, "");
        } else { //POST, PUT, DELETE
            this.body = new HttpBody(bodyType, bodyContent);
            //Set the new values for the headers content-length and content-type
            if(this.headers.getValue(HttpRequestHeaders.CONTENT_LENGTH) == null) { //If the header does not exists, we add it, else we didn't add it
                this.headers.addHeaderToHeaders(HttpRequestHeaders.CONTENT_LENGTH, Integer.toString(this.body.getContentLength()));
                this.headers.addHeaderToHeaders(HttpRequestHeaders.CONTENT_TYPE, this.body.getType().getBodyType());
            }
        }
    }

    /**
     * This method returns the string representation of the request.
     * @return The string representation of the request.
     */
    @Override
    public String toString() {
        StringBuilder request = new StringBuilder();
        request.append(getMethodName());
        request.append(WHITE_SPACE);
        request.append(url.getPath());
        request.append(WHITE_SPACE);
        request.append(getProtocolVersion());
        request.append(END_LINE);     
        request.append(getHeaders());
        request.append(END_LINE);
        request.append(getBodyContent());
        request.append(END_LINE);
        return request.toString();
    }

    /**
     * This method returns the method of the request.
     * @return The method of the request.
     */
    public Verbs getMethod() {
        return this.method;
    }

    /**
     * This method returns the method of the request as a string.
     * @return The method of the request as a string.
     */
    public String getMethodName() {
        return this.method.toString();
    }

    /**
     * This method returns the URL of the request.
     * @return The URL of the request.
     */
    public URL getUrl() {
        return this.url;
    }

    /**
     * This method returns the protocol version of the request.
     * @return The protocol version of the request.
     */
    public String getProtocolVersion() {
        return this.protocolVersion;
    }

    /**
     * This method returns the headers of the request.
     * @return The headers of the request.
     */
    public RequestHeaders getRequestHeadersObject() {
        return this.headers;
    }

    /**
     * This method returns the headers of the request as a string.
     * @return The headers of the request as a string.
     */
    public String getHeaders() {
        return this.headers.toString();
    }

    /**
     * This method returns the body of the request.
     * @return The body of the request.
     */
    public HttpBody getBody() {
        return this.body;
    }

    /**
     * This method returns the content of the body of the request.
     * @return The content of the body of the request.
     */
    public String getBodyContent() {
        return this.body.getStringContent();
    }

    /**
     * This method sets the method of the request.
     * @param method The method of the request.
     */
    public void setMethod(Verbs method) {
        this.method = method;
    }

    /**
     * This method sets the URL of the request.
     * @param url The URL of the request.
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    /**
     * This method sets the protocol version of the request.
     * @param protocolVersion The protocol version of the request.
     */
    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    /**
     * This method sets the headers of the request.
     * @param headers The headers of the request.
     */
    public void setHeaders(RequestHeaders headers) {
        this.headers = headers;
    }

    /**
     * This method sets the body of the request.
     * @param body The body of the request.
     */
    public void setBody(HttpBody body) {
        this.body = body;
    }

    /**
     * This method check if the connection needs to keep alive.
     * @return True if the connection needs to keep alive, false otherwise.
     */
    public boolean isConnectionAlive() {
        if(this.headers.getValue(HttpRequestHeaders.CONNECTION) == null) { //We first check if null, to avoid conflicts with equals
            return true;
        } else if (this.headers.getValue(HttpRequestHeaders.CONNECTION).equals("keep-alive")) { //If is not null, we check if the connection needs to keep alive
            return true;
        } else { //If not keep-alive, we supose that the connection does not need to keep alive
            return false;
        }
        
    }

    /**
     * This method adds a cookie to the headers of the request.
     * @param cookiesClient The cookie to add to the headers of the request.
     */
    public void addCookies(String cookiesClient) {
        this.headers.getHeaders().add(cookiesClient);
    }

    /**
     * This method parses a string to a request.
     * @param request The string to parse to a request.
     * @param port The port of the request.
     * @return The request parsed from the string.
     */
    public static Request parse(String request, int port) {
        String[] lines;
        if(port == HTTPUtils.HTTPS_PORT) {
            lines = request.split("\r\n");
        } else {
            lines = request.split("\n");
        }
        String[] firstLine = lines[0].split(" ");

        for (String firslineElement : firstLine) {
            firslineElement = firslineElement.split("\n")[0];
        }

        Verbs method = Verbs.valueOf(firstLine[0]);

        URL url = null;
        try {
            url = new URL("http://" + lines[1].split(" ")[1] + firstLine[1]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String protocolVersion = firstLine[2];

        String headers = "";
        int headersCount = 0;
        for (int k = 1; k < lines.length; k++) {
            if (lines[k].equals("")) {
                break;
            }
            else {
                headers += lines[k] + "\r\n";
                headersCount++;
            }
        }
        headers = headers.substring(0, headers.length() - 2);

        RequestHeaders sentHeaders = RequestHeaders.parse(headers);

        if (method == Verbs.GET || method == Verbs.HEAD) {
            return new Request(method, url, protocolVersion, sentHeaders, HttpBodyType.RAW, "");
        }
        String bodyTypeStr = sentHeaders.getValue(HttpRequestHeaders.CONTENT_TYPE); //Error al leer el CONTENT_TYPE
        HttpBodyType bodyType = HttpBodyType.parse(bodyTypeStr);
        
        StringBuilder bodyContent = new StringBuilder();
        for (int k = headersCount + 2; k < lines.length; k++) {
            bodyContent.append(lines[k]).append("\r\n");
        }
        
        return new Request(method, url, protocolVersion, sentHeaders, bodyType, bodyContent.toString());
    }
}
