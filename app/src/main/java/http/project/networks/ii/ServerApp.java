package http.project.networks.ii;


public class ServerApp {

    public static void main(String[] args) {
        GreetServer gs = GreetServer.getInstance("app/src/main/java/http/project/networks/ii/static_resources");
        gs.initServer(HTTPUtils.HTTP_PORT);
    }

}
