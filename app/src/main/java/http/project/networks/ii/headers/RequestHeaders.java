package http.project.networks.ii.headers;

import java.net.URL;

import http.project.networks.ii.utils.HttpRequestHeaders;

/**
 * This class represents the headers of the request. It contains the headers of the request.
 */
public class RequestHeaders extends Headers {

    /**
     * Empty constructor of the class RequestHeaders to parse
     */
    public RequestHeaders() {
        super();
    }

    /**
     * Constructor of the class RequestHeaders.
     * @param url The URL of the request.
     */
    public RequestHeaders(URL url) {
        super();
        // ADDING BASIC HEADERS FOR THE REQUEST MANUALLY
        addHeaderToHeaders(HttpRequestHeaders.HOST, url.getHost());
        addHeaderToHeaders(HttpRequestHeaders.ACCEPT, "*/*");
        addHeaderToHeaders(HttpRequestHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.5");
        addHeaderToHeaders(HttpRequestHeaders.CONNECTION, "keep-alive");
        addHeaderToHeaders(HttpRequestHeaders.USER_AGENT, "DJGI/1.0.0");
        addHeaderToHeaders(HttpRequestHeaders.DATE, getCurrentTime());
    }

    /**
     * Constructor of the class RequestHeaders to add arbitrary headers.
     * @param headers the headers we want to arbitrarily add
     */
    public RequestHeaders(Headers headers) {
        super();
        for (String header : headers.getHeaders()) {
            this.myHeaders.add(header);
        }
    }

    /**
     * Parse the headers string to get the RequestHeaders object from it
     * @param headersString The headers string to parse
     * @return The RequestHeaders object
     */
    public static RequestHeaders parse(String headersString) {
        String[] headers = headersString.split("\r\n");
        RequestHeaders requestHeaders = new RequestHeaders();
        for (String header : headers) {
            String[] parts = header.split(": ", 2);
            if (parts.length == 2) {
                requestHeaders.addHeaderToHeaders(parts[0], parts[1]);
            }
        }
        return requestHeaders;
    }

}
