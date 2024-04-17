package http.project.networks.ii;
import java.net.URL;

public class RequestPOST extends Request{
    public RequestPOST(URL url, String protocolVersion , RequestHeaders headers, HttpBodyType bodyType, String bodyContent) {
        super(Verbs.POST ,url, protocolVersion, headers, bodyType, bodyContent);
    }

    @Override
    public String getMethod() {
        return "POST";
    }
    
}
