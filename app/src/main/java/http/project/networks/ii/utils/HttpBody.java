package http.project.networks.ii.utils;

/**
 * A class for the body of an HTTP request
 */
public class HttpBody {
    private HttpBodyType type;
    private String content;
    private byte[] binaryContent;

    /**
     * Create a new HttpRequestBody
     * @param type the type of the body
     * @param content the content of the body
     */
    public HttpBody(HttpBodyType type, String content) {
        this.type = type;
        this.content = content;
        this.binaryContent = null;
    }

    /**
     * Create a new HttpRequestBody
     * @param type the type of the body
     * @param binaryContent the binary content of the body
     */
    public HttpBody(HttpBodyType type, byte[] binaryContent) {
        this.type = type;
        this.binaryContent = binaryContent;
        this.content = null;
    }

    /**
     * Set the type of the body
     * @param type the type of the body
     */
    public void setType(HttpBodyType type) {
        this.type = type;
    }  

    /**
     * Set the content of the body
     * @param content the content of the body
     */
    public HttpBodyType getType() {
        return this.type;
    }

    /**
     * Set the content of the body
     * @param content the content of the body
     */
    public String getStringContent() {
        return this.content;
    }

    /**
     * Set the content of the body
     * @param content the content of the body
     */
    public byte[] getBinaryContent() {
        return binaryContent;
    }

    /**
     * Set the content of the body
     * @param content the content of the body
     */
    public int getContentLength() {
        return binaryContent != null ? binaryContent.length : content.length();
    }

    /**
     * Return the body as a string
     * @param content the content of the body as a String
     */
    public String toString() {
        return this.type.getBodyType();
    }    
}
