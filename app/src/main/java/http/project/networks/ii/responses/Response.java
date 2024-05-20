package http.project.networks.ii.responses;

import http.project.networks.ii.headers.ResponseHeaders;
import http.project.networks.ii.utils.HTTPUtils;
import http.project.networks.ii.utils.HttpBodyType;
import http.project.networks.ii.utils.HttpBody;
import http.project.networks.ii.utils.HttpRequestHeaders;

/**
 * This class represents a HTTP response. It contains the status code and description, the headers and the body of the response.
 */
public class Response {
    private String statusCodeAndDescription;
    protected ResponseHeaders responseHeaders;
    private HttpBody body;

    /**
     * Constructor of the class Response.
     * @param statusCodeAndDescription The status code and description of the response.
     * @param body The body of the response.
     */
    public Response(String statusCodeAndDescription, HttpBody body) {
        this.statusCodeAndDescription = statusCodeAndDescription;
        this.responseHeaders = new ResponseHeaders();
        this.body = body;
        //For example, the response of the head method does not require a body
        if(!body.equals(null)) {
            this.responseHeaders.addHeaderToHeaders(HttpRequestHeaders.CONTENT_TYPE, this.body.getType().getBodyType());
            this.responseHeaders.addHeaderToHeaders(HttpRequestHeaders.CONTENT_LENGTH, Integer.toString(this.body.getContentLength()));
        }
    }

    /**
     * This method returns the status code and description of the response.
     * @return The status code and description of the response.
     */
    public String getStatusCodeAndDescription() {
        return this.statusCodeAndDescription;
    }

    /**
     * This method returns the headers of the response.
     * @return The headers of the response.
     */
    public ResponseHeaders getResponseHeaders() {
        return this.responseHeaders;
    }

    /**
     * This method returns the body of the response.
     * @return The body of the response.
     */
    public String getBodyContent() {
        return this.body.getStringContent();
    }

    /**
     * This method returns the binary content of the body of the response.
     * @return The binary content of the body of the response.
     */
    public byte[] getBinaryContent() {
        return this.body.getBinaryContent();
    }

    /**
     * This method returns the string representation of the status line and headers, not the body.
     * @return The string representation of the response.
     */
    public String headTypeResponse() {
        StringBuilder response = new StringBuilder();
        response.append("HTTP/1.1 " + statusCodeAndDescription);
        response.append("\r\n");
        response.append(responseHeaders.toString());
        response.append("\r\n");
        return response.toString();
    }

    /**
     * This method returns the string representation of the response.
     * @return The string representation of the response.
     */
    @Override
    public String toString() {
        StringBuilder response = new StringBuilder();
        response.append(headTypeResponse());
        if(this.body != null) {
            //If the string content is not null, write it there, else we will manage with the server to send the bytes from the body
            if(this.body.getStringContent() != null) {
                response.append(getBodyContent());
                response.append("\r\n");  
            }
        }   
        return response.toString();
    }

    /**
     * This method parses a HTTP response string and returns a Response object.
     * @param httpResponse The HTTP response string.
     * @param port The port of the server.
     * @return The Response object.
     * @throws IllegalArgumentException If the HTTP response string is null or empty, or if the format is invalid.
     */
    public static Response parse(String httpResponse, int port) throws IllegalArgumentException {
        if (httpResponse == null || httpResponse.isEmpty()) {
            throw new IllegalArgumentException("The HTTP response string cannot be null or empty");
        }
        String[] lines;
        if(port == HTTPUtils.HTTPS_PORT) {
            lines = httpResponse.split("\r\n");
        } else {
            lines = httpResponse.split("\n");
        }
        if (lines.length == 0) {
            throw new IllegalArgumentException("Invalid HTTP response format");
        }

        // Status line
        String statusLine = lines[0];
        String[] statusLineParts = statusLine.split(" ", 2);
        if (statusLineParts.length < 2) {
            throw new IllegalArgumentException("Invalid status line format");
        }
        String statusCodeAndDescription = statusLineParts[1];

        // Headers
        ResponseHeaders responseHeaders = new ResponseHeaders();
        int i = 1;
        while (i < lines.length && !lines[i].isEmpty()) {
            String headerLine = lines[i];
            int colonIndex = headerLine.indexOf(":");
            if (colonIndex == -1) {
                throw new IllegalArgumentException("Invalid header format: " + headerLine);
            }
            String headerName = headerLine.substring(0, colonIndex).trim();
            String headerValue = headerLine.substring(colonIndex + 1).trim();
            responseHeaders.addHeaderToHeaders(headerName, headerValue);
            i++;
        }

        // Body
        i++; // Skip the empty line after headers
        StringBuilder bodyBuilder = new StringBuilder();
        while (i < lines.length) {
            bodyBuilder.append(lines[i]);
            if (i < lines.length - 1) {
                bodyBuilder.append("\r\n");
            }
            i++;
        }
        String bodyContent = bodyBuilder.toString();

        // Determine body type and content
        HttpBodyType bodyType = HttpBodyType.RAW; // Default body type
        byte[] binaryContent = null;

        String contentTypeHeader = responseHeaders.getValue(HttpRequestHeaders.CONTENT_TYPE);
        if (contentTypeHeader != null) {
            bodyType = HttpBodyType.parse(contentTypeHeader);
            if (bodyType == null) {
                bodyType = HttpBodyType.RAW; // Fallback to RAW if the type is unknown
            }
        }

        HttpBody body;
        if (bodyType == HttpBodyType.RAW) {
            body = new HttpBody(bodyType, bodyContent);
        } else {
            binaryContent = bodyContent.getBytes(); // Simple conversion for demonstration
            body = new HttpBody(bodyType, binaryContent);
        }

        Response response = new Response(statusCodeAndDescription, body);
        response.responseHeaders = responseHeaders;

        return response;
    }

}
