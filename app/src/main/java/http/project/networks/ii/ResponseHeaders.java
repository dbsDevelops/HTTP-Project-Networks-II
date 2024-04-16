package http.project.networks.ii;

import java.net.URL;
import java.util.ArrayList;

public class ResponseHeaders extends Headers {

    public ResponseHeaders(URL url) {
        this.headers = new ArrayList<>();
        //DIVIDE THE HEADERS IN VERBS AND THE VALUE OF THE HEADER WILL CHAGE DEPENDING ON THE VERB
        /* 
        for (HttpHeaders headerType : HttpHeaders.values()) {
            addHeaderToHeaders(headerType, "");
        }
        */

        // ADDING BASIC HEADERS FOR THE REQUEST MANUALLY
        addHeaderToHeaders(HttpHeaders.CONNECTION, "close");
        addHeaderToHeaders(HttpHeaders.DATE, getCurrentTime());
        addHeaderToHeaders(HttpHeaders.SERVER, "DJGI/1.0.0");
    }

}
