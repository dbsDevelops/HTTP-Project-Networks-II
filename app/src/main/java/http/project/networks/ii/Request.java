package http.project.networks.ii;

import java.net.URL;

public class Request {
    public Verbs method;
    public URL url;
    public String protocolVersion;
    public SentHeaders headers; //Cambio a Headers headers (GERMAN)
    public HttpRequestBody body; 

    public Request(Verbs method,URL url, String protocolVersion, HttpBodyType bodyType, String bodyContent) {
        this.method = method;
        this.url = url;
        this.protocolVersion = protocolVersion;
        this.headers = new SentHeaders(method, url);
        if(this.method == Verbs.GET || this.method == Verbs.DELETE) {
            this.body = new HttpRequestBody(HttpBodyType.RAW, "");
        } else { //POST, PUT, HEAD
            this.body = new HttpRequestBody(bodyType, bodyContent);
            //Set the new values for the headers content-length and content-type
            this.headers.setValue(HttpHeaders.CONTENT_LENGTH, Integer.toString(this.body.getContentLength()));
            this.headers.setValue(HttpHeaders.CONTENT_TYPE, this.body.getType().getBodyType());
        }
    }

    public String toString() {
        StringBuilder request = new StringBuilder();
        request.append(this.method.toString() + " " + url.getPath() + " " + this.protocolVersion + "\r\n");
        request.append(this.headers.toString());
        return request.toString();
    }
}