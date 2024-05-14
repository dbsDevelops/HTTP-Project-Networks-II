package http.project.networks.ii.server;

import http.project.networks.ii.utils.HTTPUtils;

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
