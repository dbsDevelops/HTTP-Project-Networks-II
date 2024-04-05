/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package http.project.networks.ii;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import http.project.networks.ii.GreetClient;

class AppTest {
    @Test 
    public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() {
        GreetClient client = new GreetClient();
        client.startConnection("127.0.0.1", 6666);
        String response = client.sendMessage("hello server");
        assertEquals("hello client", response);
    }
}