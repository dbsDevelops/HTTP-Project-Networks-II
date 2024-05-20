package http.project.networks.ii.utils;

/**
 * An enum for the type of body in an HTTP request
 */
public enum HttpBodyType {
    RAW("text/plain"), 
    FORM("application/x-www-form-urlencoded"), 
    JSON("application/json"), 
    FILE("multipart/form-data"), 
    GRAPHQL("application/graphql"),
    XML("application/xml"),
    HTML("text/html"),
    JAVASCRIPT("application/javascript"),
    PHP("application/x-httpd-php"),
    CSS("text/css"),
    ICO("image/x-icon"),
    PNG("image/png"),
    JPEG("image/jpeg"),
    GIF("image/gif"),
    SVG("image/svg+xml"),
    PDF("application/pdf"),
    ZIP("application/zip"),
    TAR("application/x-tar"),
    GZIP("application/gzip"),
    BZIP2("application/x-bzip2"),
    MP4("video/mp4"),
    MPEG("audio/mpeg"),
    WAV("audio/x-wav");
    

    private String bodyType;

    /**
     * Create a new HttpBodyType
     * @param bodyType the type of body
     */
    HttpBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    /**
     * Get the type of body
     * @return the type of body
     */
    public String getBodyType() {
        return this.bodyType;
    }

    /**
     * Parse a string to get the corresponding HttpBodyType
     * @param bodyType the string to parse
     * @return the corresponding HttpBodyType
     */
    public static HttpBodyType parse(String bodyType) {
        for (HttpBodyType type : HttpBodyType.values()) {
            if (type.getBodyType().equals(bodyType)) {
                return type;
            }
        }
        return null;
    }
}
