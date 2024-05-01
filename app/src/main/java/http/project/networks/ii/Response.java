package http.project.networks.ii;

import http.project.networks.ii.utils.HttpRequestBody;
import http.project.networks.ii.utils.HttpRequestHeaders;

public class Response {
    private String statusCodeAndDescription;
    protected ResponseHeaders responseHeaders;
    private HttpRequestBody body;

    public Response(String statusCodeAndDescription, HttpRequestBody body) {
        this.statusCodeAndDescription = statusCodeAndDescription;
        this.responseHeaders = new ResponseHeaders();
        this.body = body;
        //For example, the response of the head method does not require a body
        if(!body.equals(null)) {
            this.responseHeaders.addHeaderToHeaders(HttpRequestHeaders.CONTENT_TYPE, this.body.getType().getBodyType());
            this.responseHeaders.addHeaderToHeaders(HttpRequestHeaders.CONTENT_LENGTH, Integer.toString(this.body.getContentLength()));
        }
    }

    public String getStatusCodeAndDescription() {
        return this.statusCodeAndDescription;
    }

    public ResponseHeaders getResponseHeaders() {
        return this.responseHeaders;
    }

    public String getBodyContent() {
        return this.body.getStringContent();
    }

    public byte[] getBinaryContent() {
        return this.body.getBinaryContent();
    }

    public String toString() {
        StringBuilder response = new StringBuilder();
        response.append("HTTP/1.1 " + statusCodeAndDescription);
        response.append("\r\n");
        response.append(responseHeaders.toString());
        response.append("\r\n");
        if(!this.body.equals(null)) {
            //If the string content is not null, write it there, else we will manage with the server to send the bytes from the body
            if(this.body.getStringContent() != null) {
                response.append(getBodyContent());
                response.append("\r\n");  
            }
        }   
        return response.toString();
    }

}
