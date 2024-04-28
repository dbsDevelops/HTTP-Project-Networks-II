package http.project.networks.ii;

import java.net.MalformedURLException;
import java.net.URL;

public class Request {
    public static final String END_LINE = "\r\n";
    public static final String WHITE_SPACE = " ";
    protected Verbs method;
    protected URL url;
    protected String protocolVersion;
    protected RequestHeaders headers; 
    protected HttpRequestBody body; 

    public Request(Verbs method,URL url, String protocolVersion , RequestHeaders headers, HttpBodyType bodyType, String bodyContent) {
        this.method = method;
        this.url = url;
        this.protocolVersion = protocolVersion;
        this.headers = headers;
        if(this.method == Verbs.GET || this.method == Verbs.HEAD) {
            this.body = new HttpRequestBody(HttpBodyType.RAW, "");
        } else { //POST, PUT, DELETE
            this.body = new HttpRequestBody(bodyType, bodyContent);
            //Set the new values for the headers content-length and content-type
            if(this.headers.getValue(HttpHeaders.CONTENT_LENGTH) == null) { //If the header does not exists, we add it, else we didn't add it
                this.headers.addHeaderToHeaders(HttpHeaders.CONTENT_LENGTH, Integer.toString(this.body.getContentLength()));
                this.headers.addHeaderToHeaders(HttpHeaders.CONTENT_TYPE, this.body.getType().getBodyType());
            }
        }
    }

    public String toString() {
        StringBuilder request = new StringBuilder();
        request.append(getMethod());
        request.append(WHITE_SPACE);
        request.append(url.getPath());
        request.append(WHITE_SPACE);
        request.append(getProtocolVersion());
        request.append(END_LINE);     
        request.append(getHeaders());
        request.append(END_LINE);
        request.append(getBody());
        request.append(END_LINE);
        return request.toString();
    }

    public String getMethod() {
        return this.method.toString();
    }

    public URL getUrl() {
        return this.url;
    }

    public String getProtocolVersion() {
        return this.protocolVersion;
    }

    public String getHeaders() {
        return this.headers.toString();
    }

    public String getBody() {
        return this.body.getStringContent();
    }

    public boolean isConnectionAlive() {
        if(this.headers.getValue(HttpHeaders.CONNECTION) == null) { //We first check if null, to avoid conflicts with equals
            return true;
        } else if (this.headers.getValue(HttpHeaders.CONNECTION).equals("keep-alive")) { //If is not null, we check if the connection needs to keep alive
            return true;
        } else { //If not keep-alive, we supose that the connection does not need to keep alive
            return false;
        }
        
    }

    public void addCookies(String cookiesClient) {
        this.headers.headers.add(cookiesClient);
    }

    public static Request parse(String request) {
        String[] lines = request.split("\n");
        String[] firstLine = lines[0].split(" ");

        for (String firslineElement : firstLine) {
            firslineElement = firslineElement.split("\n")[0];
        }

        Verbs method = Verbs.valueOf(firstLine[0]);

        URL url = null;
        try {
            url = new URL("http://" + lines[1].split(" ")[1] + firstLine[1]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String protocolVersion = firstLine[2];

        String headers = "";
        int headersCount = 0;
        for (int k = 1; k < lines.length; k++) {
            if (lines[k].equals("")) {
                break;
            }
            else {
                headers += lines[k] + "\r\n";
                headersCount++;
            }
        }
        headers = headers.substring(0, headers.length() - 2);

        RequestHeaders sentHeaders = RequestHeaders.parse(headers, url);

        if (method == Verbs.GET || method == Verbs.HEAD) {
            return new Request(method, url, protocolVersion, sentHeaders, HttpBodyType.RAW, "");
        }
        String bodyTypeStr = sentHeaders.getValue(HttpHeaders.CONTENT_TYPE); //Error al leer el CONTENT_TYPE
        HttpBodyType bodyType = HttpBodyType.parse(bodyTypeStr);
        
        StringBuilder bodyContent = new StringBuilder();
        for (int k = headersCount + 2; k < lines.length; k++) {
            bodyContent.append(lines[k]).append("\r\n");
        }
        
        return new Request(method, url, protocolVersion, sentHeaders, bodyType, bodyContent.toString());
    }
}
