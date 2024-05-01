package http.project.networks.ii.utils;

public class HttpRequestBody {
    private HttpBodyType type;
    private String content;
    private byte[] binaryContent;

    public HttpRequestBody(HttpBodyType type, String content) {
        this.type = type;
        this.content = content;
        this.binaryContent = null;
    }

    public HttpRequestBody(HttpBodyType type, byte[] binaryContent) {
        this.type = type;
        this.binaryContent = binaryContent;
        this.content = null;
    }

    public void setType(HttpBodyType type) {
        this.type = type;
    }  

    public HttpBodyType getType() {
        return this.type;
    }

    public String getStringContent() {
        return this.content;
    }

    public byte[] getBinaryContent() {
        return binaryContent;
    }

    public int getContentLength() {
        return binaryContent != null ? binaryContent.length : content.length();
    }

    public String toString() {
        return this.type.getBodyType();
    }    
}
