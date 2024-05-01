package http.project.networks.ii.responses;

import http.project.networks.ii.headers.Headers;
import http.project.networks.ii.utils.HttpRequestHeaders;

public class ResponseHeaders extends Headers {

    public ResponseHeaders() {
        // ADDING BASIC HEADERS FOR THE REQUEST MANUALLY
        addHeaderToHeaders(HttpRequestHeaders.CONNECTION, "close");
        addHeaderToHeaders(HttpRequestHeaders.DATE, getCurrentTime());
        addHeaderToHeaders(HttpRequestHeaders.SERVER, "DJGI/1.0.0");
    }

}
