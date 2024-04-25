package http.project.networks.ii.handleRequests;
import java.net.URL;

import http.project.networks.ii.HttpBodyType;
import http.project.networks.ii.Request;
import http.project.networks.ii.RequestHeaders;
import http.project.networks.ii.Verbs;

public class RequestHEAD extends Request{
    public RequestHEAD(URL url, String protocolVersion , RequestHeaders headers, HttpBodyType bodyType, String bodyContent) {
        super(Verbs.HEAD ,url, protocolVersion, headers, bodyType, bodyContent);
    }

    @Override
    public String getMethod() {
        return "HEAD";
    }
    
}
