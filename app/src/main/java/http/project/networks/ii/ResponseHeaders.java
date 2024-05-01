package http.project.networks.ii;

import http.project.networks.ii.headers.Headers;
import http.project.networks.ii.utils.HttpRequestHeaders;

public class ResponseHeaders extends Headers {

    public ResponseHeaders() {
        // this.myHeaders = new ArrayList<>();
        
        // ADDING BASIC HEADERS FOR THE REQUEST MANUALLY
        addHeaderToHeaders(HttpRequestHeaders.CONNECTION, "close");
        addHeaderToHeaders(HttpRequestHeaders.DATE, getCurrentTime());
        addHeaderToHeaders(HttpRequestHeaders.SERVER, "DJGI/1.0.0");
    }

}
