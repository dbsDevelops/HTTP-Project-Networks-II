package http.project.networks.ii.handle_requests;

import http.project.networks.ii.responses.Response;

/**
 * The {@code RequestCommand} interface represents a command that can be executed
 * to handle an HTTP request. Implementations of this interface will define the
 * specific logic for processing the request and generating an appropriate response.
 */
public interface RequestCommand {
    
    /**
     * Executes the command to handle the HTTP request and generates a response.
     *
     * @return a {@link Response} object representing the result of the request processing.
     */
    Response execute();
}
