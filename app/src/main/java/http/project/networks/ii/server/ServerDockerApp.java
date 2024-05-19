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
        GreetServer gs = new GreetServer(staticFilesDir, HTTPUtils.HTTP_PORT);
        gs.initServer();
    }

}
