package http.project.networks.ii;

public class Response {
    private String statusCodeAndDescription;
    private String content;

    public Response(String statusCodeAndDescription, String content) {
        this.statusCodeAndDescription = statusCodeAndDescription;
        this.content = content;
    }

    public String getStatusCodeAndDescription() {
        return this.statusCodeAndDescription;
    }

    public String getContent() {
        return this.content;
    }

    public String toString() {
        return "HTTP/1.1 " + this.statusCodeAndDescription + "\r\n" + this.content + "\r\n";
    }

}
