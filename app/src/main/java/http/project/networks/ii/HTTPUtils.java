package http.project.networks.ii;

import java.net.URL;

public class HttpUtils {
    // Port numbers
    public static final int HTTP_PORT = 80;
    public static final int HTTPS_PORT = 443;
    
    // Control values
    public static final int PORT_NOT_SET = -1;
    public static final int URL_PROTOCOL = 0;

    // Protocol strings
    public static final String HTTP_STRING = "http";
    public static final String HTTPS_STRING = "https";
    public static final String URL_SPLIT_CHARACTER = ":";

    /**
     * This method is used to automatically select with the desired url the port where the client wants to communicate
     * @param url the url where the information is going to be sent by the client
     * @return the port where the url is pointing to. If somthing goes wrong, -1 will be returned
     */
    public static int selectOperatingPort(URL url) {

        if(url.getPort() != PORT_NOT_SET) { //The method returns -1 if the port is not set with the ":" in the URL
            return url.getPort();
        }

        int port = PORT_NOT_SET;
        String host = url.getHost(); //Returns an IP of the form http/s://whatever
        String[] split = host.split(URL_SPLIT_CHARACTER);
        if(split[URL_PROTOCOL].equals(HTTP_STRING)) {
            port = HttpUtils.HTTP_PORT;
        }
        else if(split[URL_PROTOCOL].equals(HTTPS_STRING)) {
            port = HttpUtils.HTTPS_PORT;
        }
        return port;

    }

}
