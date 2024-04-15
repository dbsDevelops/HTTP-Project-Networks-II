package http.project.networks.ii;

import java.net.MalformedURLException;
import java.net.URL;

public class Request {
    public static final String END_LINE = "\r\n";
    public static final String WHITE_SPACE = " ";
    protected Verbs method;
    protected URL url;
    protected String protocolVersion;
    protected SentHeaders headers; 
    protected HttpRequestBody body; 

    public Request(Verbs method,URL url, String protocolVersion,SentHeaders headers, HttpBodyType bodyType, String bodyContent) {
        this.method = method;
        this.url = url;
        this.protocolVersion = protocolVersion;
        this.headers = headers;
        if(this.method == Verbs.GET || this.method == Verbs.HEAD) {
            this.body = new HttpRequestBody(HttpBodyType.RAW, "");
        } else { //POST, PUT, DELETE
            this.body = new HttpRequestBody(bodyType, bodyContent);
            //Set the new values for the headers content-length and content-type
            this.headers.addHeaderToHeaders(HttpHeaders.CONTENT_LENGTH, Integer.toString(this.body.getContentLength()));
            this.headers.addHeaderToHeaders(HttpHeaders.CONTENT_TYPE, this.body.getType().getBodyType());
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
        return this.body.getContent();
    }

    public static Request parse(String request) {
        String[] lines = request.split("\n");
        String[] firstLine = lines[0].split(" ");
        Verbs method;

        for (String firslineElement : firstLine) {
            firslineElement = firslineElement.split("\n")[0];
        }

        switch (firstLine[0]) {
            case "GET":
                method = Verbs.GET;
                break;
            case "HEAD":
                method = Verbs.HEAD;
                break;
            case "POST":
                method = Verbs.POST;
                break;
            case "PUT":
                method = Verbs.PUT;
                break;
            case "DELETE":
                method = Verbs.DELETE;
                break;
            default:
                throw new IllegalArgumentException("The method " + firstLine[0] + " is not supported");
        }

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

        SentHeaders sentHeaders = SentHeaders.parse(headers);

        if (method == Verbs.GET || method == Verbs.HEAD) {
            return new Request(method, url, protocolVersion, sentHeaders, HttpBodyType.RAW, "");
        }
        String bodyTypeStr = sentHeaders.getValue(HttpHeaders.CONTENT_TYPE); //Error al leer el CONTENT_TYPE
        HttpBodyType bodyType = HttpBodyType.parse(bodyTypeStr);
        // RAW("text/plain"), 
        // FORM("application/x-www-form-urlencoded"), 
        // JSON("application/json"), 
        // FILE("multipart/form-data"), 
        // GRAPHQL("application/graphql"),
        // XML("application/xml");

        switch (bodyTypeStr) {
            case "text/plain":
                bodyType = HttpBodyType.RAW;
                break;
            case "application/x-www-form-urlencoded":
                bodyType = HttpBodyType.FORM;
                break;
            case "application/json":
                bodyType = HttpBodyType.JSON;
                break;
            case "multipart/form-data":
                bodyType = HttpBodyType.FILE;
                break;
            case "application/graphql":
                bodyType = HttpBodyType.GRAPHQL;
                break;
            case "application/xml":
                bodyType = HttpBodyType.XML;
                break;
            default:
                throw new IllegalArgumentException("The body type " + bodyType + " is not supported");
        }

        
        String bodyContent = "";
        for (int k = headersCount + 2; k < lines.length; k++) {
            bodyContent += lines[k] + "\r\n";
        }
        
        return new Request(method, url, protocolVersion, sentHeaders, bodyType, bodyContent);
    }
}