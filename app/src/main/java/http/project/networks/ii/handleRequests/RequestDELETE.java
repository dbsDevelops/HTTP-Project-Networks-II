package http.project.networks.ii.handleRequests;

import http.project.networks.ii.HTTPUtils;
import http.project.networks.ii.HttpBodyType;
import http.project.networks.ii.HttpRequestBody;
import http.project.networks.ii.Response;
import http.project.networks.ii.ServerStatusCodes;
import http.project.networks.ii.TeachersClass;

public class RequestDELETE implements RequestCommand {
    private final String path;
    private final TeachersClass teachers;
    private final HttpRequestBody body;

    public RequestDELETE(String path, TeachersClass teachers, HttpRequestBody body) {
        this.path = path;
        this.teachers = teachers;
        this.body = body;
    }

    @Override
    public Response execute() {
        if (!path.equals(HTTPUtils.TEACHERS_PATH)) {
            return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
        }
        return processBody(body);
    }

    private Response processBody(HttpRequestBody body) {
        if (body.getType() == HttpBodyType.JSON) {
            String content = body.getStringContent();
            if (content != null && !content.isEmpty()) {
                String[] idsToDelete = content.split(",");
                for (String idStr : idsToDelete) {
                    try {
                        int id = Integer.parseInt(idStr.trim());
                        boolean deleted = teachers.removeTeacher(id);
                        if (!deleted) {
                            // Teacher with the given ID not found
                            return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "Teacher with ID " + id + " not found"));
                        }
                    } catch (NumberFormatException e) {
                        // Handle invalid ID format
                        return new Response(ServerStatusCodes.BAD_REQUEST_400.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "Invalid teacher ID format"));
                    }
                }
                return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "Teachers deleted successfully"));
            } else {
                // No teacher IDs provided in the JSON body
                return new Response(ServerStatusCodes.BAD_REQUEST_400.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "No teacher IDs provided in the request body"));
            }
        }
        return new Response(ServerStatusCodes.BAD_REQUEST_400.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.INVALID_REQUEST_BODY));
    }
}
