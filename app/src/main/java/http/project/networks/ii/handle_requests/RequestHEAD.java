package http.project.networks.ii.handle_requests;

import http.project.networks.ii.responses.Response;
import http.project.networks.ii.server.ServerStatusCodes;
import http.project.networks.ii.utils.HTTPUtils;
import http.project.networks.ii.utils.HttpBodyType;
import http.project.networks.ii.utils.HttpBody;

/**
 * The {@code RequestHEAD} class handles HTTP HEAD requests for retrieving only the headers of a resource.
 * It implements the {@link RequestCommand} interface.
 */
public class RequestHEAD implements RequestCommand {
    private final String path;

    /**
     * Constructs a {@code RequestHEAD} instance.
     *
     * @param path the path of the resource for which headers are requested.
     */
    public RequestHEAD(String path) {
        this.path = path;
    }

    /**
     * Executes the HEAD request by processing the specified path and returning only the headers.
     *
     * @return a {@link Response} object containing only the headers for the requested resource.
     */
    @Override
    public Response execute() {
        if (path.equals(HTTPUtils.TEACHERS_PATH)) {
            // Process HEAD request here (HEAD requests typically only return headers)
            return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpBody(HttpBodyType.RAW, "Headers for " + HTTPUtils.TEACHERS_PATH));
        }

        return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
    }
}
