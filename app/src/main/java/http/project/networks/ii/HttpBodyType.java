package http.project.networks.ii;

public enum HttpBodyType {
    RAW("text/plain"), 
    FORM("application/x-www-form-urlencoded"), 
    JSON("application/json"), 
    FILE("multipart/form-data"), 
    GRAPHQL("application/graphql"),
    XML("application/xml");
    // HTML("text/html"),
    // JAVASCRIPT("application/javascript"),
    // CSS("text/css"),
    // PNG("image/png"),
    // JPEG("image/jpeg"),
    // GIF("image/gif"),
    // SVG("image/svg+xml"),
    // PDF("application/pdf"),
    // ZIP("application/zip"),
    // TAR("application/x-tar"),
    // GZIP("application/gzip"),
    // BZIP2("application/x-bzip2"),

    private String bodyType;

    HttpBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getBodyType() {
        return this.bodyType;
    }
}