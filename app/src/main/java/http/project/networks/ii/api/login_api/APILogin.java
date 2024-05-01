package http.project.networks.ii.api.login_api;

import java.util.List;

import http.project.networks.ii.requests.Request;
import http.project.networks.ii.responses.Response;

public class APILogin {

    private List<User> users = new java.util.ArrayList<>();

    public APILogin() {
        users.add(new User("admin", "admin", "admin"));

    }

    public Response readRequest(Request request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readRequest'");
    }
    
}
