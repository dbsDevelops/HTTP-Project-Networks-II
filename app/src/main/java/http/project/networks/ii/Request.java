package http.project.networks.ii;

import java.net.MalformedURLException;
import java.net.URL;

public class Request {
    public static final String END_LINE = "\r\n";
    public Verbs method;
    public URL url;
    public String protocolVersion;
    public SentHeaders headers; 
    public HttpRequestBody body; 

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
        request.append(this.method.toString());
        request.append(" ");
        request.append(url.getPath());
        request.append(" ");
        request.append(this.protocolVersion);
        request.append(END_LINE);     
        request.append(this.headers.toString());
        request.append(END_LINE);
        request.append(this.body.getContent());
        request.append(END_LINE);
        return request.toString();
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
        for (int k = 1; k < lines.length; k++) {
            if (lines[k].equals("")) {
                break;
            }
            else {
                headers += lines[k] + "\r\n";
            }
        }
        headers = headers.substring(0, headers.length() - 2);

        SentHeaders sentHeaders = SentHeaders.parse(headers);

        HttpBodyType bodyType = HttpBodyType.RAW;
        String bodyContent = "";
        


        return new Request(method, url, protocolVersion, sentHeaders, bodyType, bodyContent);
    }
}