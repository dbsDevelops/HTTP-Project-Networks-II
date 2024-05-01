package http.project.networks.ii.server;

import http.project.networks.ii.utils.HTTPUtils;

public class ServerApp {

    public static void main(String[] args) {
        GreetServer gs = new GreetServer("app/src/main/java/http/project/networks/ii/static_resources");
        gs.initServer(HTTPUtils.HTTP_PORT);
    }

}
