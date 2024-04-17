package http.project.networks.ii;
import java.net.URL;

public class RequestPUT extends Request{
    public RequestPUT(URL url, String protocolVersion , RequestHeaders headers, HttpBodyType bodyType, String bodyContent) {
        super(Verbs.PUT ,url, protocolVersion, headers, bodyType, bodyContent);
    }

    @Override
    public String getMethod() {
        return "PUT";
    }
    
}
