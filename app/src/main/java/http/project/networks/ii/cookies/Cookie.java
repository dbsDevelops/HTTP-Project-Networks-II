package http.project.networks.ii.cookies;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * The {@code Cookie} class represents a simple cookie that can be used to store information on the client side.
 */
public class Cookie {
    private static final String COOKIE = "cookie";
    private static final String MAX_AGE = "Max-Age";
    private static final String SECURE = "Secure";
    private static final String DATE = "Date";
    private static final String HTTP_ONLY = "HttpOnly";
    private static final int MIN_EXPIRATION = 20; // Minimum expiration time in seconds
    private static final int MAX_EXPIRATION = 60; // Maximum expiration time in seconds

    private static int count = 1;
    private int id;
    private String name;
    private String value;
    private int maxAge;
    private boolean secure;
    private LocalDateTime timeStartCookie;

    /**
     * Constructor for the Cookie class
     * The cookie is created with a random name, value, and expiration time
     * The expiration time is between 20 and 60 seconds
     * The cookie is not HTTPS by default but it can be set to secure with the setSecure method if TLS is used
     * The time when the cookie was created is stored in the timeStartCookie variable
     * The count is used to generate a unique id for the cookie
     * The count is incremented each time a cookie is created
     */
    public Cookie() {
        Random random = new Random();
        this.name = "cookie"+count;
        this.id = count;
        this.value = "ma" + (count+365) + "xrz";
        this.maxAge = MIN_EXPIRATION + random.nextInt(MAX_EXPIRATION - MIN_EXPIRATION + 1);
        this.secure = false; //By default the cookie is not secure
        this.timeStartCookie = LocalDateTime.now();
        count++; //Increment the count in a way that is difficol to guess
    }

    /**
     * Constructor for the used for parse cookie
     * @param id the id of the cookie
     * @param value the value of the cookie
     * @param maxAge the expiration time of the cookie
     * @param secure if the cookie is secure
     * @param timeStartCookie the time when the cookie was created
     */
    public Cookie(int id, String value, int maxAge, boolean secure, LocalDateTime timeStartCookie) {
        this.id = id;
        this.name= "cookie"+id;
        this.value = value;
        this.maxAge = maxAge;
        this.secure = secure;
        this.timeStartCookie = timeStartCookie;
    }

    /**
     * Set the secure value of the cookie
     * @param secure {@code true} if the cookie is secure, {@code false} otherwise
     */
    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    /**
     * Get the name of the cookie
     * @return the name of the cookie
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Get the value of the cookie
     * @return the value of the cookie
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Get the id of the cookie
     * @return the id of the cookie
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get the time when the cookie was created
     * @return the time when the cookie was created
     */
    public LocalDateTime getTimeStartCookie() {
        return this.timeStartCookie;
    }

    /**
     * Get the expiration time of the cookie
     * @return the expiration time of the cookie
     */
    public int getMaxAge() {
        return this.maxAge;
    }

    /**
     * Parse a cookie string to get the cookie object
     * The cookie string is in the format "cookie1=value; Max-Age=20; Secure; HttpOnly; Date=2021-06-01T12:00:00"
     * @param cookie the cookie string
     * @return the cookie object
     */
    public static Cookie parse(String cookie) {
        String[] parts = cookie.split("; ");
        int id = 0; //Invalid cookie (always starting in 1)
        String value = "";
        int maxAge = 0;
        boolean secure = false;
        LocalDateTime timeStartCookie = null;

        for(String part: parts) {
            String[] keyValue = part.split("=", 2);
            String key = keyValue[0];
            if (key.startsWith(COOKIE)) {
                id = Integer.parseInt(key.substring(6)); // "cookie" has 6 characters
                value = keyValue[1];
            }else if(key.equals(MAX_AGE)) {
                maxAge = Integer.parseInt(keyValue[1]);
            } else if(key.equals(SECURE)) {
                secure = true;
            } else if(key.equals(DATE)) {
                timeStartCookie = LocalDateTime.parse(keyValue[1]);
            }
        }
        
        return new Cookie(id,value,maxAge,secure,timeStartCookie);
    }

    /**
     * Get the string representation of the cookie
     * @return the string representation of the cookie
     */
    public String toString() {
        StringBuilder cookie = new StringBuilder();
        cookie.append(name+"=");
        cookie.append(value);
        cookie.append("; "+MAX_AGE+"=");
        cookie.append(maxAge);
        if (secure) { //If the cookie is HTTPS only
            cookie.append("; "+SECURE);
        }
        cookie.append("; "+HTTP_ONLY);
        cookie.append("; "+DATE+"=");
        cookie.append(this.timeStartCookie.toString()); // Add the start date of the cookie
        return cookie.toString(); 
    }

    /**
     * Check if two cookies are equal by comparing their id, value, expiration time, secure flag, and start time
     * @param cookie the cookie to compare
     * @return true if the cookies are equal, false otherwise
     */
    public boolean equals(Cookie cookie) {
        return this.id == cookie.getId() && this.value.equals(cookie.getValue())
         && this.maxAge == cookie.getMaxAge() && this.secure == cookie.secure &&
          this.timeStartCookie.equals(cookie.getTimeStartCookie());
    }
}
