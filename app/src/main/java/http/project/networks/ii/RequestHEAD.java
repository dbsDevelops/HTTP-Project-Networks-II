package http.project.networks.ii;
import java.net.URL;

public class RequestHEAD extends Request{
    public RequestHEAD(URL url, String protocolVersion , RequestHeaders headers, HttpBodyType bodyType, String bodyContent) {
        super(Verbs.HEAD ,url, protocolVersion, headers, bodyType, bodyContent);
    }

    @Override
    public String getMethod() {
        return "HEAD";
    }
    
}
