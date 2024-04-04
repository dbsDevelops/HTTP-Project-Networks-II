package project.code;

import java.util.List;
import java.util.ArrayList;

public class SentHeaders {
    List<String> headers;
    public SentHeaders(Verbs method) {
        this.headers = new ArrayList<>();
        //DIVIDE THE HEADERS IN VERBS AND THE VALUE OF THE HEADER WILL CHAGE DEPENDING ON THE VERB
        for (HttpHeaders headerType : HttpHeaders.values()) {
            addHeader(headerType, "");
        }
        setValue(HttpHeaders.ACCEPT, "*/*");
        setValue(HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.5");
        setValue(HttpHeaders.CONNECTION, "keep-alive");
        setValue(HttpHeaders.CONTENT_LENGTH, "0"); //Used for POST requests
        setValue(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded"); //Used for POST requests
        setValue(HttpHeaders.USER_AGENT, "Mozilla/5.0");
        setValue(HttpHeaders.DATE, getCurrentTime());
    }

    public void addHeaderToHeaders(Enum headerType, String headerValue) {
        String header = headerType.toString() + ": " + headerValue;
        this.headers.add(header);
    }

    public String getValue(Enum headerType) {
        for (String header : this.headers) {
            if (header.contains(headerType.toString())) {
                return header.split(": ")[1];
            }
        }
        return null;
    }
    
    public void setValue(Enum headerType, String headerValue) {
        for (int i = 0; i < this.headers.size(); i++) {
            if (this.headers.get(i).contains(headerType.toString())) {
                this.headers.set(i, headerType.toString() + ": " + headerValue);
            }
        }
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