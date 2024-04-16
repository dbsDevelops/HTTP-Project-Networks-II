package http.project.networks.ii;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Files;

public class HTTPUtils {
    // Port numbers
    public static final int HTTP_PORT = 80;
    public static final int HTTPS_PORT = 443;
    
    // Control values
    public static final int PORT_NOT_SET = -1;
    public static final int URL_PROTOCOL = 0;

    // Protocol strings
    public static final String HTTP_STRING = "http";
    public static final String HTTPS_STRING = "https";

    // Splitting characters
    public static final String URL_SPLIT_CHARACTER = ":";
    public static final String SLASH_CHARACTER = "/";
    public static final String SPACE_CHARACTER = " ";
    public static final String NEW_LINE_CHARACTER = "\n";

    // Paths
    public static final String TEACHERS_PATH = "/teachers";

    // Resources
    public static final String RESOURCE_NOT_FOUND = "Resource not found";
    public static final String RESOURCE_CREATED = "Resource created";
    public static final String RESOURCE_UPDATED = "Resource updated";
    public static final String RESOURCE_DELETED = "Resource deleted";

    // Methods
    public static final String METHOD_NOT_ALLOWED = "Method not allowed";

    // Clients
    public static final String CLIENT_CONNECTED = "Client connected";
    public static final String CLIENT_CONNECTION_CLOSED = "Client connection closed!";

    // Servers
    public static final String SERVER_IS_RUNNING_ON_PORT = "Server is running on port: ";
    public static final String COULD_NOT_LISTEN_ON_PORT = "Could not listen on port: ";

    // Responses
    public static final String RESPONSE = "Response: \n";
    public static final String ESTIMATED_RESPONSE_SIZE = "Estimated response size: ";

    //To map extensions and create a new body depending on the extension that the file has
    private static final Map<String, HttpBodyType> extensionToTypeMap = new HashMap<String, HttpBodyType>();
    
    static {
        extensionToTypeMap.put("txt", HttpBodyType.RAW);
        extensionToTypeMap.put("json", HttpBodyType.JSON);
        extensionToTypeMap.put("xml", HttpBodyType.XML);
        extensionToTypeMap.put("html", HttpBodyType.HTML);
        extensionToTypeMap.put("js", HttpBodyType.JAVASCRIPT);
        extensionToTypeMap.put("css", HttpBodyType.CSS);
        extensionToTypeMap.put("png", HttpBodyType.PNG);
        extensionToTypeMap.put("jpeg", HttpBodyType.JPEG);
        extensionToTypeMap.put("jpg", HttpBodyType.JPEG);
        extensionToTypeMap.put("gif", HttpBodyType.GIF);
        extensionToTypeMap.put("svg", HttpBodyType.SVG);
        extensionToTypeMap.put("pdf", HttpBodyType.PDF);
        extensionToTypeMap.put("zip", HttpBodyType.ZIP);
        extensionToTypeMap.put("tar", HttpBodyType.TAR);
        extensionToTypeMap.put("gz", HttpBodyType.GZIP);
        extensionToTypeMap.put("bz2", HttpBodyType.BZIP2);
    }
    
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
            port = HTTPUtils.HTTP_PORT;
        }
        else if(split[URL_PROTOCOL].equals(HTTPS_STRING)) {
            port = HTTPUtils.HTTPS_PORT;
        }
        return port;

    }

    public static HttpRequestBody createBodyFromFile(String filePath) throws IOException {

        Path path = Paths.get(filePath);
        String content = Files.readString(path);
        String fileExtension = getFileExtension(path);

        HttpBodyType type = extensionToTypeMap.getOrDefault(fileExtension, HttpBodyType.RAW); // Default to RAW if unknown
        return new HttpRequestBody(type, content);
        
    }

    private static String getFileExtension(Path path) {
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) return "";
        return fileName.substring(dotIndex + 1).toLowerCase();
    }

}
