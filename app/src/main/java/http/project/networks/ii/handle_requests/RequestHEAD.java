package http.project.networks.ii.handle_requests;

import http.project.networks.ii.responses.Response;
import http.project.networks.ii.server.ServerStatusCodes;
import http.project.networks.ii.utils.HTTPUtils;
import http.project.networks.ii.utils.HttpBodyType;
import http.project.networks.ii.utils.HttpBody;

public class RequestHEAD implements RequestCommand {
    private final String path;

    public RequestHEAD(String path) {
        this.path = path;
    }

    @Override
    public Response execute() {
        if (path.equals(HTTPUtils.TEACHERS_PATH)) {
            // Process HEAD request here (HEAD requests typically only return headers)
            return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpBody(HttpBodyType.RAW, "Headers for " + HTTPUtils.TEACHERS_PATH));
        }


        return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));        
    }
}

