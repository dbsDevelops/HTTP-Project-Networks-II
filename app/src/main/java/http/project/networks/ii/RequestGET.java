package http.project.networks.ii;
import java.net.URL;

public class RequestGET extends Request{
    public RequestGET(URL url, String protocolVersion , RequestHeaders headers, HttpBodyType bodyType, String bodyContent) {
        super(Verbs.GET ,url, protocolVersion, headers, bodyType, bodyContent);
    }

    @Override
    public String getMethod() {
        return "GET";
    }
    
}
