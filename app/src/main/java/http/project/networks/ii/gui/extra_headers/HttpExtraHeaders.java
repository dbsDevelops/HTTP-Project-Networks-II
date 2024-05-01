package http.project.networks.ii.gui.extra_headers;

import java.util.ArrayList;
import java.util.List;

public enum HttpExtraHeaders {
    ACCEPT_CHARSET("Accept-Charset"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_DATETIME("Accept-Datetime"),
    AUTHORIZATION("Authorization"),
    CACHE_CONTROL("Cache-Control"),
    CONTENT_MD5("Content-MD5"),
    EXPECT("Expect"),
    FORWARDED("Forwarded"),
    FROM("From"),
    HOST("Host"),
    IF_MATCH("If-Match"),
    IF_MODIFIED_SINCE("If-Modified-Since"),
    IF_NONE_MATCH("If-None-Match"),
    IF_RANGE("If-Range"),
    IF_UNMODIFIED_SINCE("If-Unmodified-Since"),
    MAX_FORWARDS("Max-Forwards"),
    ORIGIN("Origin"),
    PRAGMA("Pragma"),
    PROXY_AUTHORIZATION("Proxy-Author"),
    RANGE("Range"),
    REFERER("Referer"),
    SERVER("Server"),
    TE("TE"),
    UPGRADE("Upgrade"),
    VIA("Via");

    private String header;

    HttpExtraHeaders(String header) {
        this.header = header;
    }

    public String getHeader() {
        return this.header;
    }

    public static List<String> getHeaders() {
        List<String> headers = new ArrayList<>();
        for (HttpExtraHeaders header : HttpExtraHeaders.values()) {
            headers.add(header.getHeader());
        }
        return headers;
    }

    public static HttpExtraHeaders parse(String header) {
        for (HttpExtraHeaders headerType : HttpExtraHeaders.values()) {
            if (headerType.getHeader().equals(header)) {
                return headerType;
            }
        }
        throw new IllegalArgumentException("The header "+ header + " is not found in HttpHeaders: ");
    } 
}