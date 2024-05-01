package http.project.networks.ii.handle_requests;

import http.project.networks.ii.responses.Response;

public interface RequestCommand {
    Response execute();
}
