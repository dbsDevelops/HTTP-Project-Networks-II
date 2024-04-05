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
            System.out.println("Please enter the form body: ");
        } else if (type == HttpBodyType.JSON) {
            System.out.println("Please enter the JSON body: ");
        } else if (type == HttpBodyType.FILE) {
            System.out.println("Please enter the file body: ");
        } else if (type == HttpBodyType.GRAPHQL) {
            System.out.println("Please enter the GraphQL body: ");
        } else if (type == HttpBodyType.XML) {
            System.out.println("Please enter the XML body: ");
        }
        
    }

    public void buildRawBody(String content) {
        System.out.println("Raw body: " + content);
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
