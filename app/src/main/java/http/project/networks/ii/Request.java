package http.project.networks.ii;

import java.net.URL;

public class Request {
    public Verbs method;
    public URL url;
    public String protocolVersion;
    public SentHeaders headers; //Cambio a Headers headers (GERMAN)
    public HttpRequestBody body; 

    public Request(Verbs method,URL url, String protocolVersion, HttpBodyType bodyType) {
        this.method = method;
        this.url = url;
        this.protocolVersion = protocolVersion;
        this.headers = new SentHeaders(method, url);
        this.body = new HttpRequestBody(bodyType);
    }

    public String toString() {
        StringBuilder request = new StringBuilder();
        request.append(this.method.toString() + " " + url.getPath() + " " + this.protocolVersion + "\r\n");
        request.append(this.headers.toString());
        return request.toString();
    }
}