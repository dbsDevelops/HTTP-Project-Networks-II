package ProjectCode;

import java.util.List;
import java.util.ArrayList;

public class SentHeaders {
    List<String> headers;
    public SentHeaders() {
        this.headers = new ArrayList<>();
        for (DictionaryHeaders headerType : DictionaryHeaders.values()) {
            addHeader(headerType, "");
        }
    }

    public void addHeader(Enum headerType, String headerValue) {
        String header = headerType.toString() + ": " + headerValue;
        this.headers.add(header);
    }

    public String getValue(Enum headerType) {
        for (String header : this.headers) {
            if (header.contains(headerType.toString())) {
                return header.split(": ")[1];
            }
        }
        return null;
    }
    
    public void setValue(Enum headerType, String headerValue) {
        for (int i = 0; i < this.headers.size(); i++) {
            if (this.headers.get(i).contains(headerType.toString())) {
                this.headers.set(i, headerType.toString() + ": " + headerValue);
            }
        }
    }

    public String toString() {
        String headers = "";
        for (String header : this.headers) {
            headers += header + "\r\n";
        }
        return headers;
    }
}