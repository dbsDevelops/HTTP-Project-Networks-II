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
    public SentHeaders(Verbs method, URL url) {
        this.headers = new ArrayList<>();
        //DIVIDE THE HEADERS IN VERBS AND THE VALUE OF THE HEADER WILL CHAGE DEPENDING ON THE VERB
        for (HttpHeaders headerType : HttpHeaders.values()) {
            addHeaderToHeaders(headerType, "");
        }
        setValue(HttpHeaders.HOST, url.getHost());
        setValue(HttpHeaders.ACCEPT, "/");
        setValue(HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.5");
        setValue(HttpHeaders.CONNECTION, "keep-alive");
        setValue(HttpHeaders.USER_AGENT, "Mozilla/5.0");
        setValue(HttpHeaders.DATE, getCurrentTime());
    
        if (method == Verbs.POST || method == Verbs.PUT) {
            setValue(HttpHeaders.CONTENT_LENGTH, "0"); //Used for POST and PUT requests
            setValue(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded"); //Used for POST and PUT requests
        }
    }

    public void addHeaderToHeaders(HttpHeaders headerType, String headerValue) {
        String header = headerType.toString() + ": " + headerValue;
        this.headers.add(header);
    }

    public String getValue(HttpHeaders headerType) {
        for (String header : this.headers) {
            String[] parts = header.split(": ", 2);
            if (parts[0].equals(headerType.toString())) {
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
        String headers = "";
        for (String header : this.headers) {
            headers += header + "\r\n";
        }
        return headers;
    }
}