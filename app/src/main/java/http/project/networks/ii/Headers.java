package http.project.networks.ii;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import http.gui.extra_headers.HttpExtraHeaders;

import java.util.Date;
import java.text.SimpleDateFormat;

public abstract class Headers {
    List<String> myHeaders;

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
        for (String header : this.myHeaders) {
            headersString.append(header).append("\r\n");
        }
        return headersString.toString();
    }

}