package http.project.networks.ii.server;

import http.project.networks.ii.utils.HTTPUtils;

/**
 * This class represents the server application. It creates two threads, one for the HTTPS server and one for the HTTP server.
 */
public class ServerApp {

    /**
     * Main method of the server application. It creates two threads, one for the HTTPS server and one for the HTTP server.
     * @param args The arguments of the main method.
     */
    public static void main(String[] args) {
        // Create a runnable for the HTTPS server
        Runnable httpsServerTask = () -> {
            GreetServer gshttps = new GreetServer("app/src/main/java/http/project/networks/ii/static_resources", HTTPUtils.HTTPS_PORT);
            gshttps.initServer();
        };

        // Create a runnable for the HTTP server
        Runnable httpServerTask = () -> {
            GreetServer gshttp = new GreetServer("app/src/main/java/http/project/networks/ii/static_resources", HTTPUtils.HTTP_PORT);
            gshttp.initServer();
        };

        // Create threads for each server task
        Thread httpsThread = new Thread(httpsServerTask);
        Thread httpThread = new Thread(httpServerTask);

        // Start the threads
        httpsThread.start();
        httpThread.start();

        // Wait for the threads to finish
        try {
            httpsThread.join();
            httpThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
