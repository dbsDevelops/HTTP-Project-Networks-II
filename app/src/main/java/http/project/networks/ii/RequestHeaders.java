package http.project.networks.ii;

import java.net.URL;
import java.util.ArrayList;

public class RequestHeaders extends Headers {

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

    public static RequestHeaders parse(String string) {
        String[] headers = string.split("\r\n");
        URL url = null;
        try {
            url = new URL("http://" + headers[1].split(" ")[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestHeaders sentHeaders = new RequestHeaders(url);
        for (String header : headers) {
            String[] parts = header.split(": ", 2);
            String headerType = parts[0];
            sentHeaders.addHeaderToHeaders(headerType, parts[1]);
        }
        return sentHeaders;
    }

}
