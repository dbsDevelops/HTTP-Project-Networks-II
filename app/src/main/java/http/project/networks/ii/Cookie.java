package http.project.networks.ii;

import java.time.LocalDateTime;

public class Cookie {
    private static int count = 1;
    private String name;
    private String value;
    private int maxAge;
    private boolean secure;
    private LocalDateTime timeStartCookie;

    public Cookie() {
        this.name = "cookie"+count;
        this.value = "ma" + (count+365) + "xrz";
        this.maxAge = 3600; //seconds
        this.secure = false; //No TLS de momento
        this.timeStartCookie = LocalDateTime.now();
        count++; //Increment the count in a way that is difficol to guess
    }

    public String getName() {
        return this.name;
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

    public static Cookie parse(String cookie){
        String[] parts = cookie.split("; ");
        Cookie newCookie = new Cookie();
        for(String part: parts) {
            String[] keyValue = part.split("=", 2);
            if(keyValue[0].equals("id")) {
                newCookie.value = keyValue[1];
            } else if(keyValue[0].equals("Max-Age")) {
                newCookie.maxAge = Integer.parseInt(keyValue[1]);
            } else if(keyValue[0].equals("Secure")) {
                newCookie.secure = true;
            } else if(keyValue[0].equals("Date")) {
                newCookie.timeStartCookie = LocalDateTime.parse(keyValue[1]);
            }
        }
        return newCookie;
    }

    public String toString() {
        StringBuilder cookie = new StringBuilder();
        cookie.append(name+"=");
        cookie.append(value);
        cookie.append("; Max-Age=");
        cookie.append(maxAge);
        if (secure) { //If the cookie is HTTPS only
            cookie.append("; Secure");
        }
        cookie.append("; HttpOnly");
        cookie.append("; Date=");
        cookie.append(this.timeStartCookie.toString()); // Add the start date of the cookie
        //cookie.append("\r\n");
        return cookie.toString(); 
    }

    public boolean equals(Cookie cookie) {
        return this.value.equals(cookie.value);
    }
}
