package http.project.networks.ii.utils;

public enum HttpBodyType {
    RAW("text/plain"), 
    FORM("application/x-www-form-urlencoded"), 
    JSON("application/json"), 
    FILE("multipart/form-data"), 
    GRAPHQL("application/graphql"),
    XML("application/xml"),
    HTML("text/html"),
    JAVASCRIPT("application/javascript"),
    CSS("text/css"),
    PNG("image/png"),
    JPEG("image/jpeg"),
    GIF("image/gif"),
    SVG("image/svg+xml"),
    PDF("application/pdf"),
    ZIP("application/zip"),
    TAR("application/x-tar"),
    GZIP("application/gzip"),
    BZIP2("application/x-bzip2"),
    MP4("video/mp4");

    private String bodyType;

    HttpBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getBodyType() {
        return this.bodyType;
    }

    public static HttpBodyType parse(String bodyType) {
        for (HttpBodyType type : HttpBodyType.values()) {
            if (type.getBodyType().equals(bodyType)) {
                return type;
            }
        }
        return null;
    }
}
