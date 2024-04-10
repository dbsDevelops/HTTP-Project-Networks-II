package http.project.networks.ii;

public class HttpRequestBody {
    private HttpBodyType type;
    private String content;

    public HttpRequestBody(HttpBodyType type, String content) {
        this.type = type;
        this.content = content;
    }

    public void setType(HttpBodyType type) {
        this.type = type;
    }  

    public HttpBodyType getType() {
        return this.type;
    }

    public String getContent() {
        return this.content;
    }

    public int getContentLength() {
        return this.content.length();
    }

    public String toString() {
        return this.type.getBodyType();
    }    
}
