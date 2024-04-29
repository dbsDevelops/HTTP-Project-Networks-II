package http.project.networks.ii;

import java.util.List;

import http.project.networks.ii.API.loginAPI.User;

public class APILogin {

    private List<User> users = new java.util.ArrayList<User>();

    public APILogin() {
        users.add(new User("admin", "admin", "admin"));

    }

    public Response readRequest(Request request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readRequest'");
    }
    
}
