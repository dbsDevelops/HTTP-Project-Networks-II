package http.project.networks.ii;

import java.net.URL;

public class HTTPUtils {

    public static final int HTTP_PORT = 80;
    public static final int HTTPS_PORT = 443;

    /**
     * This method is used to automatically select with the desired url the port where the client wants to communicate
     * @param url the url where the information is going to be sent by the client
     * @return the port where the url is pointing to. If somthing goes wrong, -1 will be returned
     */
    public static int selectOperatingPort(URL url) {

        if(url.getPort() != -1) { //The method returns -1 if the port is not set with the ":" in the URL
            return url.getPort();
        }

        int port = -1;
        String host = url.getHost(); //Returns an IP of the form http/s://whatever
        String[] split = host.split(":");
        if(split[0].equals("http")) {
            port = HTTPUtils.HTTP_PORT;
        }
        else if(split[0].equals("https")) {
            port = HTTPUtils.HTTPS_PORT;
        }
        return port;

    }

}
