package http.project.networks.ii.headers;

import http.project.networks.ii.utils.HttpRequestHeaders;

public class ResponseHeaders extends Headers {

    /**
     * Create a new ResponseHeaders object with the basic headers for the response
     */
    public ResponseHeaders() {
        // ADDING BASIC HEADERS FOR THE REQUEST MANUALLY
        addHeaderToHeaders(HttpRequestHeaders.CONNECTION, "close");
        addHeaderToHeaders(HttpRequestHeaders.DATE, getCurrentTime());
        addHeaderToHeaders(HttpRequestHeaders.SERVER, "DJGI/1.0.0");
    }

}
