package http.project.networks.ii;

public class Response {
    private String statusCodeAndDescription;
    private ResponseHeaders responseHeaders;
    private HttpRequestBody body;

    public Response(String statusCodeAndDescription, HttpRequestBody body) {
        this.statusCodeAndDescription = statusCodeAndDescription;
        this.responseHeaders = new ResponseHeaders();
        this.body = body;
        //For example, the response of the head method does not require a body
        if(!body.equals(null)) {
            this.responseHeaders.addHeaderToHeaders(HttpHeaders.CONTENT_TYPE, this.body.getType().getBodyType());
            this.responseHeaders.addHeaderToHeaders(HttpHeaders.CONTENT_LENGTH, Integer.toString(this.body.getContentLength()));
        }
    }

    public String getStatusCodeAndDescription() {
        return this.statusCodeAndDescription;
    }

    public String getContent() {
        return this.body.getContent();
    }

    public String toString() {
        StringBuilder request = new StringBuilder();
        request.append("HTTP/1.1 " + statusCodeAndDescription);
        request.append("\r\n");
        request.append(responseHeaders.toString());
        request.append("\r\n");
        if(!this.body.equals(null)) {
            request.append(getContent());
            request.append("\r\n");  
        }   
        return request.toString();
    }

}
