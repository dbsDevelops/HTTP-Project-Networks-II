package http.project.networks.ii.headers;

import java.net.URL;

import http.project.networks.ii.utils.HttpRequestHeaders;

public class RequestHeaders extends Headers {

    //Empty constructor for the Server parse
    public RequestHeaders() {
        super();
    }

    public RequestHeaders(URL url) {
        super();
        // ADDING BASIC HEADERS FOR THE REQUEST MANUALLY
        addHeaderToHeaders(HttpRequestHeaders.HOST, url.getHost());
        addHeaderToHeaders(HttpRequestHeaders.ACCEPT, "/");
        addHeaderToHeaders(HttpRequestHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.5");
        addHeaderToHeaders(HttpRequestHeaders.CONNECTION, "keep-alive");
        addHeaderToHeaders(HttpRequestHeaders.USER_AGENT, "Mozilla/5.0");
        addHeaderToHeaders(HttpRequestHeaders.DATE, getCurrentTime());
    }

    /**
     * Parse the headers string to get the RequestHeaders object from it
     * @param headersString
     * @return the RequestHeaders object
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
