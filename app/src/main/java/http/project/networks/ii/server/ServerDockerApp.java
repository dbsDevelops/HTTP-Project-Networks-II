package http.project.networks.ii.server;

import http.project.networks.ii.utils.HTTPUtils;

/**
 * Class used for generate a .jar file to be executed inside a docker container. The main of this class takes 
 * the value of the environment variable "STATIC_FILES_DIR". If this variable is null, the defaulr static files 
 * of the server are taken.
 */
public class ServerDockerApp {

    public static void main(String[] args) {
        
        String staticFilesDir = System.getenv("STATIC_FILES_DIR");
        if (staticFilesDir == null) {
            staticFilesDir = "/HTTP-Project-Networks-II/app/src/main/java/http/project/networks/ii/static_resources";
        }

        //To enclose into a runnable, the variable must be final
        final String staticFilesDirFinal = staticFilesDir;

        // Create a runnable for the HTTPS server
        Runnable httpsServerTask = () -> {
            GreetServer gshttps = new GreetServer(staticFilesDirFinal, HTTPUtils.HTTPS_PORT);
            gshttps.initServer();
        };

        // Create a runnable for the HTTP server
        Runnable httpServerTask = () -> {
            GreetServer gshttp = new GreetServer(staticFilesDirFinal, HTTPUtils.HTTP_PORT);
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
