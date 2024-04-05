package http.project.networks.ii;

public enum HttpHeaders {
    ACCEPT("Accept"),
    //ACCEPT_CHARSET("Accept-Charset"),
    //ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept-Language"),
    ACCEPT_DATETIME("Accept-Datetime"),
    //AUTHORIZATION("Authorization"),
    //CACHE_CONTROL("Cache-Control"),
    CONNECTION("Connection"),
    //COOKIE("Cookie"),
    CONTENT_LENGTH("Content-Length"),
    //CONTENT_MD5("Content-MD5"),
    CONTENT_TYPE("Content-Type"),
    DATE("Date"),
    //EXPECT("Expect"),
    //FORWARDED("Forwarded"),
    //FROM("From"),
    HOST("Host"),
    //IF_MATCH("If-Match"),
    //IF_MODIFIED_SINCE("If-Modified-Since"),
    //IF_NONE_MATCH("If-None-Match"),
    //IF_RANGE("If-Range"),
    //IF_UNMODIFIED_SINCE("If-Unmodified-Since"),
    //MAX_FORWARDS("Max-Forwards"),
    //ORIGIN("Origin"),
    //PRAGMA("Pragma"),
    //PROXY_AUTHORIZATION("Proxy-Author)
    //RANGE("Range"),
    //REFERER("Referer"),
    //TE("TE"),
    USER_AGENT("User-Agent"); // Importante poner un punto y coma al final del enum
    //UPGRADE("Upgrade"),
    //VIA("Via"),

    private String header;

    HttpHeaders(String header) {
        this.header = header;
    }

    public String getHeader() {
        return this.header;
    }
}