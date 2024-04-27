package http.project.networks.ii.handleRequests;

import http.project.networks.ii.HttpBodyType;

public class HttpRequestBody {
    private final HttpBodyType type;
    private final String content;

    public HttpRequestBody(HttpBodyType type, String content) {
        this.type = type;
        this.content = content;
    }

    public HttpBodyType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
