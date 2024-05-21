package http.project.networks.ii.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.net.URL;

import java.time.Duration;
import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import http.project.networks.ii.cookies.Cookie;
import http.project.networks.ii.requests.Request;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey; // Import the SecretKey class
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * A utility class for HTTP
 */
public class HTTPUtils {

    // Private constructor to avoid instantiation
    private HTTPUtils() {}

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

    // Responses
    public static final String METHOD_NOT_ALLOWED = "Method not allowed";
    public static final String NOT_FOUND = "Not found";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error";
    public static final String INVALID_REQUEST_BODY = "Invalid request body";
    public static final String INVALID_TEACHER_FORMAT = "Invalid teacher format";

    // Clients
    public static final String CLIENT_CONNECTED = "Client connected";
    public static final String CLIENT_CONNECTION_CLOSED = "Client connection closed!";

    // Servers
    public static final String SERVER_IS_RUNNING_ON_PORT = "Server is running on port: ";
    public static final String COULD_NOT_LISTEN_ON_PORT = "Could not listen on port: ";

    // Responses
    public static final String RESPONSE = "Response: \n";
    public static final String ESTIMATED_RESPONSE_SIZE = "Estimated response size: ";

    /**
     * This HashMap contains all the file extensions as keys, that maps to the corresponding HttpBodyType header as value
     */
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
        extensionToTypeMap.put("mp4", HttpBodyType.MP4);
        extensionToTypeMap.put("mp3", HttpBodyType.MPEG);
        extensionToTypeMap.put("wav", HttpBodyType.WAV);
        extensionToTypeMap.put("php", HttpBodyType.PHP);
        extensionToTypeMap.put("ico", HttpBodyType.ICO);
        extensionToTypeMap.put("wasm", HttpBodyType.WASM);
        extensionToTypeMap.put("data", HttpBodyType.DATA);
    }
    
    /**
     * This method is used to automatically select with the desired url the port where the client wants to communicate
     * @param url The url where the information is going to be sent by the client
     * @return The port where the url is pointing to. If somthing goes wrong, -1 will be returned
     */
    public static int selectPortBasedOnProtocol(URL url) {
        int port = -1; // Valor predeterminado si algo va mal
    
        String protocol = url.getProtocol(); // Obtiene el protocolo de la URL (http o https)
    
        if(url.getPort() != -1) {
            return url.getPort(); //If port is specified with the : return this port
        }

        if(protocol.equals("http")) {
            port = url.getPort() != -1 ? url.getPort() : HTTPUtils.HTTP_PORT;
        }
        else if(protocol.equals("https")) {
            port = url.getPort() != -1 ? url.getPort() : HTTPUtils.HTTPS_PORT;
        }
    
        return port;
    }

    /**
     * Method that resolves a path, takes care of determining the file type and constructing an appropriate body 
     * and then including that body in a response.
     * @param localPath Path where the static resources are located inside the local device
     * @param filePath Path obtained from te request, that contains the final file/directory
     * @return A constructed body with his Body type correctly assigned
     * @throws IOException
     */
    public static HttpBody createRequestBodyFromFile(String localPath, String filePath) throws IOException {
        String filePathString = localPath + filePath;
        Path path = Paths.get(filePathString);
        if(!path.toFile().exists()) {
            return null;
        }
        if(path.toFile().isDirectory()) {
            if(path.resolve("index.html").normalize().toFile().exists()) {
                path = path.resolve("index.html").normalize();
                System.out.println("Path: " + path);
                String content = Files.readString(path);
                return new HttpBody(HttpBodyType.HTML, content);
            }
            String html = buildDirectoryHtml(localPath, filePath);
            return new HttpBody(HttpBodyType.HTML, html);
        } else {
            HttpBodyType type = determineBodyType(path);
            if (isBinaryType(type)) {
                byte[] data = Files.readAllBytes(path);
                return new HttpBody(type, data);
            } else {
                String content = Files.readString(path);
                return new HttpBody(type, content);
            }
        }
    }

    /**
     * Method that auto-generates an html code with all the entries of a directory for its later encapsulation inside a body.
     * @param localPath Path where the static resources are located inside the local device
     * @param dirPath Directory path that we want to generate the html
     * @return A string with the html code of the directory
     * @throws IOException
     */
    private static String buildDirectoryHtml(String localPath, String dirPath) throws IOException {
        Path directoryPath = Paths.get(localPath, dirPath).normalize();
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><head><title>Index of ");
        htmlBuilder.append(dirPath);
        htmlBuilder.append("</title></head><body><h1>Index of ");
        htmlBuilder.append(dirPath);
        htmlBuilder.append("</h1><ul>");

        // List files and directories
        try (Stream<Path> stream = Files.list(directoryPath)) {
            for (Path entry : stream.collect(Collectors.toList())) {
                String fileName = entry.getFileName().toString();
                Path relativeFilePath = Paths.get(dirPath, fileName);

                htmlBuilder.append("<li><a href=\"");
                if(relativeFilePath.toFile().isDirectory()) {
                    htmlBuilder.append(relativeFilePath.toString() + "/");
                } else {
                    htmlBuilder.append(relativeFilePath.toString());
                }
                htmlBuilder.append("\">");
                htmlBuilder.append(fileName);
                htmlBuilder.append("</a></li>");
            }
        }

        htmlBuilder.append("</ul></body></html>");
        return htmlBuilder.toString();
    }

    /**
     * This method is used to check if a cookie is expired
     * Compare the time when the cookie was created with the current time
     * @param cookie The cookie to check
     * @return True if the cookie is expired, false otherwise
     */
    public static Boolean isExpiredCookie(Cookie cookie){
        if(Duration.between(cookie.getTimeStartCookie(), LocalDateTime.now()).getSeconds() > cookie.getMaxAge()){
            return true;
        }
        return false;
    }


    /**
     * This method is used to check if a cookie exists in the server
     * Compare the cookie with the cookies in the request headers to check if it exists
     * @param request The request of the client to check 
     * @param cookie The cookie to check
     * @return True if the cookie exists, false otherwise
     */
    public static boolean existServerCookie(Request request, Cookie cookie) {
        for(String header: request.getRequestHeadersObject().getHeaders()) {
            if(header.startsWith(HttpRequestHeaders.SET_COOKIE.getHeader())) {
                if(Cookie.parse(header).equals(cookie)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method is used to determine the body type of a file
     * @param path The path of the file
     * @return The body type of the file
     */
    private static HttpBodyType determineBodyType(Path path) {
        String extension = getFileExtension(path);
        return extensionToTypeMap.getOrDefault(extension, HttpBodyType.RAW);
    }

    /**
     * This method is used to check if a body type is binary
     * @param type The body type to check
     * @return True if the body type is binary, false otherwise
     */
    private static boolean isBinaryType(HttpBodyType type) {
        return type == HttpBodyType.PNG || type == HttpBodyType.JPEG || type == HttpBodyType.GIF ||
               type == HttpBodyType.SVG || type == HttpBodyType.PDF || type == HttpBodyType.ZIP ||
               type == HttpBodyType.TAR || type == HttpBodyType.GZIP || type == HttpBodyType.BZIP2 || 
               type == HttpBodyType.MP4 || type == HttpBodyType.MPEG || type == HttpBodyType.WAV ||
               type == HttpBodyType.ICO || type == HttpBodyType.WASM || type == HttpBodyType.DATA;
    }

    /**
     * This method is used to get the extension of a file
     * @param path The path of the file
     * @return The extension of the file
     */
    private static String getFileExtension(Path path) {
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) return "";
        return fileName.substring(dotIndex + 1).toLowerCase();
    }

    /**
     * This method is used to encrypt a message
     * @param message The message to encrypt
     * @param symmetricKey The symmetric key to use
     * @return The encrypted message
     * @throws Exception If the encryption fails
     */
    public static String encryptMessage(byte[] message, SecretKey symmetricKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(symmetricKey.getEncoded(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedMessage = cipher.doFinal(message);
        //System.out.println(Base64.getEncoder().encodeToString(encryptedMessage));
        return Base64.getEncoder().encodeToString(encryptedMessage); //To avoid socket issues
    }

    /**
     * This method is used to decrypt a message
     * @param encryptedMessage The message to decrypt
     * @param symmetricKey The symmetric key to use
     * @return The decrypted message
     * @throws Exception If the decryption fails
     */
    public static String decryptMessage(byte[] encryptedMessage, SecretKey symmetricKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(encryptedMessage);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(symmetricKey.getEncoded(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedMessage = cipher.doFinal(bytes);
        return new String(decryptedMessage, StandardCharsets.UTF_8);
    }

}
