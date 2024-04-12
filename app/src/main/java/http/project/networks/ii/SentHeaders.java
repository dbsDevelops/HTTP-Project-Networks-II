package http.project.networks.ii;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Date;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SentHeaders {
    List<String> headers;
    public SentHeaders(URL url) {
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

    public void addHeaderToHeaders(HttpHeaders headerType, String headerValue) {
        String header = headerType.getHeader() + ": " + headerValue;
        this.headers.add(header);
    }

    public String getValue(HttpHeaders headerType) {
        for (String header : this.headers) {
            String[] parts = header.split(": ", 2);
            if (parts[0].equals(headerType.getHeader())) {
                return parts[1]; // Value of the header type found
            }
        }
        throw new IllegalArgumentException("The header "+ headerType + " is not found in HttpHeaders: ");
    }
    
    public void setValue(HttpHeaders headerType, String headerValue) {
        for (int i = 0; i < this.headers.size(); i++) {
            String[] parts = this.headers.get(i).split(": ", 2);
            if (parts[0].equals(headerType.toString())) {
                this.headers.set(i, headerType.toString() + ": " + headerValue);
                return;
            }
        }
        throw new IllegalArgumentException("The header "+ headerType + " is not found in HttpHeaders: ");
    }

    private String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(new Date());
    }

    public String toString() {
        StringBuilder headersString = new StringBuilder();
        for (String header : this.headers) {
            headersString.append(header).append("\r\n");
        }
        return headersString.toString();
    }

    public static SentHeaders parse(String string) {
        String[] headers = string.split("\r\n");
        URL url = null;
        try {
            url = new URL("http://" + headers[1].split(" ")[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SentHeaders sentHeaders = new SentHeaders(url);
        for (String header : headers) {
            String[] parts = header.split(": ", 2);
            HttpHeaders headerType = HttpHeaders.parse(parts[0]);
            sentHeaders.addHeaderToHeaders(headerType, parts[1]);
        }
        return sentHeaders;
    }
}