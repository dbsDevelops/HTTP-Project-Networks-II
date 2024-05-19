package http.project.networks.ii.client;

import http.project.networks.ii.headers.RequestHeaders;
import http.project.networks.ii.requests.Request;
import http.project.networks.ii.utils.HttpBodyType;
import http.project.networks.ii.utils.Verbs;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.net.URL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Class corresponding to the .jar generated during the construction of the project with gradle, called ClientCLI.jar. We 
 * give credits to curl, as we have been inspired by it to make the CLI.
 * This class is very similar to the usual command line clients (like curl), as it will be in charge of passing the arguments 
 * passed by the user for later interpretation. After that, it will send a request to the server for the indicated URL and will 
 * be in charge of processing and displaying the server's response.
 */
public class ClientCLI {
    public static void main(String[] args) {
        OptionParser parser = new OptionParser();
        parser.acceptsAll(List.of("X", "request"), "HTTP method type").withRequiredArg().defaultsTo("GET");
        parser.acceptsAll(List.of("H", "header"), "HTTP headers").withRequiredArg();
        parser.acceptsAll(List.of("d", "data"), "Data to be sent as the request body").withRequiredArg();
        parser.acceptsAll(List.of("h", "help"), "Display help").forHelp();

        OptionSpec<String> urlSpec = parser.nonOptions("URL to request").ofType(String.class).describedAs("url");

        OptionSet options = parser.parse(args);

        String method = (String) options.valueOf("request");
        String data = (String) options.valueOf("data");
        List<?> headers = options.valuesOf("header");
        String urlString = options.valueOf(urlSpec);

        if (options.has("help") || urlString == null) {
            printHelp(parser);
            return;
        }

        try {
            URL url = new URL(urlString);
            GreetClient client = new GreetClient();
            RequestHeaders requestHeaders = new RequestHeaders(url);
            for (Object header : headers) {
                String[] splitHeader = ((String) header).split(":");
                if (splitHeader.length == 2) {
                    requestHeaders.addHeaderToHeaders(splitHeader[0].trim(), splitHeader[1].trim());
                }
            }

            Verbs verb = Verbs.valueOf(method.toUpperCase());
            HttpBodyType bodyType = HttpBodyType.JSON;  // Default, adjust based on actual data type
            Request request = new Request(verb, url, "HTTP/1.1", requestHeaders, bodyType, data != null ? data : "");
            
            client.sendRequest(url, request);
            client.getResponseString();
        } catch (MalformedURLException e) {
            System.err.println("Invalid URL: " + urlString);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void printHelp(OptionParser parser) {
        try {
            System.out.println("Usage: java -jar app/build/libs/app.jar [url] (params...)");
            parser.printHelpOn(System.out);
        } catch (IOException e) {
            System.err.println("Could not print help: " + e.getMessage());
        }
    }
}
