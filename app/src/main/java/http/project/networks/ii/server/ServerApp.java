package http.project.networks.ii.server;

import http.project.networks.ii.utils.HTTPUtils;

public class ServerApp {

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

        // Optionally, join the threads if you need the main thread to wait for them to finish
        try {
            httpsThread.join();
            httpThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
