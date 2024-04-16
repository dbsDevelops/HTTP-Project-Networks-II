package http.project.networks.ii;


public class ServerApp {

    public static void main(String[] args) {
        GreetServer gs = new GreetServer("app/src/main/java/http/project/networks/ii/static_resources");
        gs.initServer();
    }

}
