package http.project.networks.ii;

import java.net.URL;
import java.util.ArrayList;

public class RequestHeaders extends Headers {

    //Empty constructor for the Server parse
    public RequestHeaders() {
        this.headers = new ArrayList<>();
    }

    public RequestHeaders(URL url) {
        this.headers = new ArrayList<>();
        //DIVIDE THE HEADERS IN VERBS AND THE VALUE OF THE HEADER WILL CHAGE DEPENDING ON THE VERB
        /* 
        for (HttpHeaders headerType : HttpHeaders.values()) {
            addHeaderToHeaders(headerType, "");
        }
        */

        // ADDING BASIC HEADERS FOR THE REQUEST MANUALLY
        addHeaderToHeaders(HttpHeaders.HOST, url.getHost());
        addHeaderToHeaders(HttpHeaders.ACCEPT, "/");
        addHeaderToHeaders(HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.5");
        addHeaderToHeaders(HttpHeaders.CONNECTION, "keep-alive");
        addHeaderToHeaders(HttpHeaders.USER_AGENT, "Mozilla/5.0");
        addHeaderToHeaders(HttpHeaders.DATE, getCurrentTime());
    }

    public static RequestHeaders parse(String headersString, URL url) {
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