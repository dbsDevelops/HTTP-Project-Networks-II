package ProjectCode;

public class Request {
    public Verbs method;
    public String url;
    public String protocolVersion;
    public String headers; //Cambio a Headers headers (GERMAN)
    public String body; //Posible cambio a Body body (GERMAN)

    public Request(Verbs method,String url, String protocolVersion, String headers, String body) {
        this.method = method;
        this.url = url;
        this.protocolVersion = protocolVersion;
        this.headers = headers;
        this.body = body;
    }

    public String toString() {
        return method.toString() + this.url + this.protocolVersion + "\r\n" + this.headers + "\r\n" + this.body + "\r\n";
    }
}