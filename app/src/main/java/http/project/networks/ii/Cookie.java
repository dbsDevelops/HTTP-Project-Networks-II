package http.project.networks.ii;

import java.time.LocalDateTime;

public class Cookie {
    private static int idName = 1976;
    private String value;
    private int maxAge;
    private boolean secure;
    private LocalDateTime timeStartCookie;

    public Cookie() {
        this.value = "cookie" + idName;
        this.maxAge = 3600; //seconds
        this.secure = false; //No TLS de momento
        this.timeStartCookie = LocalDateTime.now();
        idName += 233; //Increment the idName in a way that is difficol to guess
    }

    public String getValue() {
        return this.value;
    }

    public LocalDateTime getTimeStartCookie() {
        return this.timeStartCookie;
    }

    public int getMaxAge() {
        return this.maxAge;
    }

    public String buildCookie() {
        StringBuilder cookie = new StringBuilder();
        //cookie.append(HttpHeaders.SET_COOKIE.getHeader() + ": ");
        cookie.append("name=");
        cookie.append(value);
        cookie.append("; Max-Age=");
        cookie.append(maxAge);
        if (secure) { //If the cookie is HTTPS only
            cookie.append("; Secure");
        }
        cookie.append("; HttpOnly");

        return cookie.toString();    
    }
}
