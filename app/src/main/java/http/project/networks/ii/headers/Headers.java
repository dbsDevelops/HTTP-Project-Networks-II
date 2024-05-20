package http.project.networks.ii.headers;

import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import http.project.networks.ii.gui.extra_headers.HttpExtraHeaders;
import http.project.networks.ii.utils.HttpRequestHeaders;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * This class represents the headers of the request. It contains the headers of the request.
 */
public abstract class Headers {
    List<String> myHeaders;

    /**
     * Constructor of the class Headers.
     */
    protected Headers() {
        this.myHeaders = new ArrayList<>();
    }

    /**
     * This method returns the headers of the request.
     * @return The headers of the request.
     */
    public List<String> getHeaders() {
        return this.myHeaders;
    }

    /**
     * Add the header to the headers list of the request headers using the HttpRequestHeaders enum type
     * @param headerType The type of header to add
     * @param headerValue The value of the header to add
     */
    public void addHeaderToHeaders(HttpRequestHeaders headerType, String headerValue) {
        String header = headerType.getHeader() + ": " + headerValue;
        this.myHeaders.add(header);
    }

    /**
     * Add the header to the headers list of the request headers using the HttpExtraHeaders enum type
     * @param headerType The type of header to add
     * @param headerValue The value of the header to add
     */
    public void addHeaderToHeaders(HttpExtraHeaders headerType, String headerValue) {
        String header = headerType.getHeader() + ": " + headerValue;
        this.myHeaders.add(header);
    }

    /**
     * Add the header string to the headers list of the request headers
     * @param headerType The type of header to add
     * @param headerValue The value of the header to add
     */
    public void addHeaderToHeaders(String headerType, String headerValue) {
        String header = headerType + ": " + headerValue;
        this.myHeaders.add(header);
    }

    /**
     * Remove the header from the headers
     * @param headerType The type of header to remove
     * @param headerValue The value of the header to remove
     */
    public void removeHeader(HttpRequestHeaders headerType, String headerValue) {
        String header = headerType.getHeader() + ": " + headerValue;
        this.myHeaders.remove(header);
    }

    /**
     * Get the value of the header type
     * @param headerType
     * @return A string the value of the header type
     */
    public String getValue(HttpRequestHeaders headerType) {
        for (String header : this.myHeaders) {
            String[] parts = header.split(": ", 2);
            if (parts[0].equals(headerType.getHeader())) {
                return parts[1]; // Value of the header type found
            }
        }
        return null;
    }
    
    /**
     * Set the value of the header type
     * @param headerType The type of header where we want to set the value
     * @param headerValue The value to set
     */
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

    /**
     * Get the value of the current time in GMT format
     * @return A string with the current time
     */
    protected String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(new Date());
    }
    
    /**
     * Get the string representation of the headers
     * @return A string with the headers
     */
    @Override
    public String toString() {
        StringBuilder headersString = new StringBuilder();
        for (String header : this.myHeaders) {
            headersString.append(header).append("\r\n");
        }
        return headersString.toString();
    }

}