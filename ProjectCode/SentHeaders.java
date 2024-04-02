package ProjectCode;

import java.util.List;
import java.util.ArrayList;

public class SentHeaders {
    List<String> headers;
    public SentHeaders() {
        this.headers = new ArrayList<>();
    }

    public void addHeader(Enum headerType, String headerValue) {
        String header = headerType.toString() + ": " + headerValue;
        this.headers.add(header);
    }
}