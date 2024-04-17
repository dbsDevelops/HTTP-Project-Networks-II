package http.project.networks.ii;


public class Cookie {
    private String name;
    private String value;
    private int maxAge;
    private String path;
    private boolean secure;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    private String buildCookieHashed(){
        StringBuilder cookie = new StringBuilder();
        cookie.append(name);
        cookie.append("=");
        cookie.append(value);
        if (maxAge > 0) {
            cookie.append("; Max-Age=");
            cookie.append(maxAge);
        }
        if (path != null) {
            cookie.append("; Path=");
            cookie.append(path);
        }
        if (secure) {
            cookie.append("; Secure");
        }

        // Encrypt the cookie
        try {
            return HTTPUtils.EncryptMessage(cookie.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }

    public String toString() {
        //nombre=valor; expires=fecha; path=ruta; domain=dominio; secure
        return buildCookieHashed();
    }
}
