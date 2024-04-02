package ProjectCode;

import java.util.List;
import java.util.ArrayList;

public class SentHeaders {
    List<String> headers;
    public SentHeaders() {
        this.headers = new ArrayList<>();
    }

    public void addHeader(String header) {
        this.headers.add(header);
    }
}