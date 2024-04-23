package http.project.networks.ii;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Date;
import java.text.SimpleDateFormat;

public abstract class Headers {
    List<String> headers;

    public void addHeaderToHeaders(HttpHeaders headerType, String headerValue) {
        String header = headerType.getHeader() + ": " + headerValue;
        this.headers.add(header);
    }

    public void addHeaderToHeaders(String headerType, String headerValue) {
        String header = headerType + ": " + headerValue;
        this.headers.add(header);
    }

    public void removeHeader(HttpHeaders headerType, String headerValue) {
        String header = headerType.getHeader() + ": " + headerValue;
        this.headers.remove(header);
    }

    public String getValue(HttpHeaders headerType) {
        for (String header : this.headers) {
            String[] parts = header.split(": ", 2);
            if (parts[0].equals(headerType.getHeader())) {
                return parts[1]; // Value of the header type found
            }
        }
        return null;
    }
    
    public void setValue(HttpHeaders headerType, String headerValue) {
        for (int i = 0; i < this.headers.size(); i++) {
            String[] parts = this.headers.get(i).split(": ", 2);
            if (parts[0].equals(headerType.getHeader())) {
                this.headers.set(i, headerType.getHeader() + ": " + headerValue);
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
/* 
    //use it only for client cookies
    public void addCookieToClient(Cookie cookie) {
        String cookieValue = getValue(HttpHeaders.COOKIE);
        if(cookieValue != null) {
            StringBuilder newCookie = new StringBuilder(cookieValue);
            newCookie.append("; ");
            newCookie.append(cookie.toString());
            setValue(HttpHeaders.COOKIE, newCookie.toString());
        } else {
            addHeaderToHeaders(HttpHeaders.COOKIE, cookie.toString());
        }
    }

    //Use it only for client cookies
    public void removeCookieFromClient(Cookie cookie) {
        String cookieValue = getValue(HttpHeaders.COOKIE);
        if(cookieValue != null) {
            String[] cookies = cookieValue.split("; ");
            StringBuilder newCookie = new StringBuilder();
            for (String c : cookies) {
                if (!c.equals(cookie.toString())) {
                    newCookie.append(c);
                    newCookie.append("; ");
                }
            }
            setValue(HttpHeaders.COOKIE, newCookie.toString());
        }
    }
*/
    public String toString() {
        StringBuilder headersString = new StringBuilder();
        for (String header : this.headers) {
            headersString.append(header).append("\r\n");
        }
        return headersString.toString();
    }

}