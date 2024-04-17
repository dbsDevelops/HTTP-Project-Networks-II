package http.project.networks.ii;
import java.net.URL;

public class RequestDELETE extends Request{
    public RequestDELETE(URL url, String protocolVersion , RequestHeaders headers, HttpBodyType bodyType, String bodyContent) {
        super(Verbs.DELETE ,url, protocolVersion, headers, bodyType, bodyContent);
    }

    @Override
    public String getMethod() {
        return "DELETE";
    }


}



///https://refactoring.guru/design-patterns/command