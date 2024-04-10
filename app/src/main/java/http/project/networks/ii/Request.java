package http.project.networks.ii;

import java.net.URL;

public class Request {
    public static final String END_LINE = "\r\n";
    public Verbs method;
    public URL url;
    public String protocolVersion;
    public SentHeaders headers; 
    public HttpRequestBody body; 

    public Request(Verbs method,URL url, String protocolVersion,SentHeaders headers, HttpBodyType bodyType, String bodyContent) {
        this.method = method;
        this.url = url;
        this.protocolVersion = protocolVersion;
        this.headers = headers;
        if(this.method == Verbs.GET || this.method == Verbs.HEAD) {
            this.body = new HttpRequestBody(HttpBodyType.RAW, "");
        } else { //POST, PUT, DELETE
            this.body = new HttpRequestBody(bodyType, bodyContent);
            //Set the new values for the headers content-length and content-type
            this.headers.addHeaderToHeaders(HttpHeaders.CONTENT_LENGTH, Integer.toString(this.body.getContentLength()));
            this.headers.addHeaderToHeaders(HttpHeaders.CONTENT_TYPE, this.body.getType().getBodyType());
        }
    }

    public String toString() {
        StringBuilder request = new StringBuilder();
        request.append(this.method.toString());
        request.append(" ");
        request.append(url.getPath());
        request.append(" ");
        request.append(this.protocolVersion);
        request.append(END_LINE);
        request.append(this.headers.toString());
        request.append(END_LINE);
        request.append(this.body.getContent());
        request.append(END_LINE);
        return request.toString();
    }
}