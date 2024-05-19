package http.project.networks.ii.headers;

import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import http.project.networks.ii.gui.extra_headers.HttpExtraHeaders;
import http.project.networks.ii.utils.HttpRequestHeaders;

import java.util.Date;
import java.text.SimpleDateFormat;

public abstract class Headers {
    List<String> myHeaders;

    protected Headers() {
        this.myHeaders = new ArrayList<>();
    }

    public List<String> getHeaders() {
        return this.myHeaders;
    }

    public void addHeaderToHeaders(HttpRequestHeaders headerType, String headerValue) {
        String header = headerType.getHeader() + ": " + headerValue;
        this.myHeaders.add(header);
    }

    public void addHeaderToHeaders(HttpExtraHeaders headerType, String headerValue) {
        String header = headerType.getHeader() + ": " + headerValue;
        this.myHeaders.add(header);
    }

    public void addHeaderToHeaders(String headerType, String headerValue) {
        String header = headerType + ": " + headerValue;
        this.myHeaders.add(header);
    }

    public void removeHeader(HttpRequestHeaders headerType, String headerValue) {
        String header = headerType.getHeader() + ": " + headerValue;
        this.myHeaders.remove(header);
    }

    public String getValue(HttpRequestHeaders headerType) {
        for (String header : this.myHeaders) {
            String[] parts = header.split(": ", 2);
            if (parts[0].equals(headerType.getHeader())) {
                return parts[1]; // Value of the header type found
            }
        }
        return null;
    }
    
    public void setValue(HttpRequestHeaders headerType, String headerValue) {
        for (int i = 0; i < this.myHeaders.size(); i++) {
            String[] parts = this.myHeaders.get(i).split(": ", 2);
            if (parts[0].equals(headerType.getHeader())) {
                this.myHeaders.set(i, headerType.getHeader() + ": " + headerValue);
                return;
            }
        }
        throw new IllegalArgumentException("The header "+ headerType + " is not found in HttpHeaders: ");
    }

    protected String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(new Date());
    }
    
    public String toString() {
        StringBuilder headersString = new StringBuilder();
        for (String header : this.myHeaders) {
            headersString.append(header).append("\r\n");
        }
        return headersString.toString();
    }

}