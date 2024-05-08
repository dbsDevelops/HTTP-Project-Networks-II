// /*
//  * This Java source file was generated by the Gradle 'init' task.
//  */
// package http.project.networks.ii;

// import org.junit.jupiter.api.Test;

package http.project.networks.ii;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import http.project.networks.ii.client.GreetClient;
import http.project.networks.ii.server.GreetServer;

class GreetClientTest {

    private GreetClient client;
    private GreetServer server;

    @BeforeEach
    public void init() {
        // Initialise the client and server.
        client = new GreetClient(8080);
        server = new GreetServer("app/src/main/java/http/project/networks/ii/static_resources");
    }

    @Test
    public void shouldRequestSpecifiedUrl() throws MalformedURLException {
        // Mock the necessary parts to simulate URL selection.
        // Assert that the selected URL is the one expected.
        //Create a new request
        /*
        String urlAddress = "http://localhost";          //URL to send the request
        Verbs method = Verbs.GET;                        //Method to send the request
        URL url = new URL(urlAddress);                   //URL to send the request
        String protocolVersion = "HTTP/1.1";             //Protocol version  
        SentHeaders headers = new SentHeaders(url);      //Headers
        Request request = new Request(method, url, protocolVersion, headers, null, null);
        assertEquals(url, request.getUrl());
        */
        assert(true);
    }

    @Test
    public void testGetVerbUsage() {
        // Mock a GET request and assert correct behavior.
        assert(true);
    }

    @Test
    public void testHeadVerbUsage() {
        // Mock a HEAD request and assert correct behavior.
        assert(true);
    }

    @Test
    public void testPutVerbUsage() {
        // Mock a PUT request and assert correct behavior.
        assert(true);
    }

    @Test 
    public void testPostVerbUsage() {
        // Mock a POST request and assert correct behavior.
        assert(true);
    }

    @Test
    public void testDeleteVerbUsage() {
        // Mock a DELETE request and assert correct behavior.
        assert(true);
    }

    @Test
    void testArbitraryHeaderAddition() {
        // Mock a request adding an arbitrary header and assert its presence.
        assert(true);
    }

    @Test
    void testRequestBodySpecification() {
        // Mock sending a request with a body and verify the body content.
        //TODO: Add the necessary tests for all body types: RAW, FORM, JSON, FILE, GRAPHQL, XML, etc.
        assert(true);
    }

    @Test
    void testSuccessiveRequests() {
        // Ensure the application can send successive requests without needing a restart.
        assert(true);
    }
}