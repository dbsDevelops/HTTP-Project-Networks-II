package http.project.networks.ii;

public class Response {
    private int statusCode;
    private String status;
    private String content;

    public Response(int statusCode, String status, String content) {
        this.statusCode = statusCode;
        this.status = status;
        this.content = content;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatus() {
        return this.status;
    }

    public String getContent() {
        return this.content;
    }

    public String toString() {
        return "HTTP/1.1 " + this.statusCode + " " + this.status + "\r\n" + this.content + "\r\n";
    }
    
    public void send() {
        System.out.println(this.toString());
    }

}
