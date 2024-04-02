public class Request {
    public Verbs verb;
    public String httpVersion;
    public String headers; //Cambio a Headers headers (GERMAN)
    public String body; //Posible cambio a Body body (GERMAN)

    public Request(Verbs verb, String httpVersion, String headers, String body) {
        this.verb = verb;
        this.httpVersion = httpVersion;
        this.headers = headers;
        this.body = body;
    }

    public String toString() {
        return verb.toString() + " / " + this.httpVersion + "\r\n" + this.headers + "\r\n" + this.body + "\r\n";
    }
}