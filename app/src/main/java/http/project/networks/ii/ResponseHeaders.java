package http.project.networks.ii;

import java.util.ArrayList;

public class ResponseHeaders extends Headers {

    public ResponseHeaders() {
        this.headers = new ArrayList<>();
        
        // ADDING BASIC HEADERS FOR THE REQUEST MANUALLY
        addHeaderToHeaders(HttpHeaders.CONNECTION, "close");
        addHeaderToHeaders(HttpHeaders.DATE, getCurrentTime());
        addHeaderToHeaders(HttpHeaders.SERVER, "DJGI/1.0.0");
    }

}
