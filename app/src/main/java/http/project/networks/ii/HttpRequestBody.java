package http.project.networks.ii;

public class HttpRequestBody {
    private HttpBodyType type;
    private String content;

    public HttpRequestBody(HttpBodyType type, String content) {
        this.type = type;
        this.content = content;
        if (type == HttpBodyType.RAW) {
            buildRawBody(this.content);
        } else if (type == HttpBodyType.FORM) {
            buildFormBody(this.content);
        } else if (type == HttpBodyType.JSON) {
            buildJsonBody(this.content);
        } else if (type == HttpBodyType.FILE) {
            buildFileBody(this.content);
        } else if (type == HttpBodyType.GRAPHQL) {
            buildGraphQLBody(this.content);
        } else if (type == HttpBodyType.XML) {
            buildXmlBody(this.content);
        }
    }

    public void buildRawBody(String content) {
        System.out.println("Raw body: " + content);
    }

    public void buildFormBody(String content) {
        System.out.println("Form body: " + content);
    }

    public void buildJsonBody(String content) {
        System.out.println("JSON body: " + content);
    }

    public void buildFileBody(String content) {
        System.out.println("File body: " + content);
    }

    public void buildGraphQLBody(String content) {
        System.out.println("GraphQL body: " + content);
    }

    public void buildXmlBody(String content) {
        System.out.println("XML body: " + content);
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
