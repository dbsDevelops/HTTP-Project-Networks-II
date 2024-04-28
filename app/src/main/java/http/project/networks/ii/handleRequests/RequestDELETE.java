package http.project.networks.ii.handleRequests;

import com.google.gson.Gson;

import http.project.networks.ii.HTTPUtils;
import http.project.networks.ii.HttpBodyType;
import http.project.networks.ii.HttpRequestBody;
import http.project.networks.ii.Response;
import http.project.networks.ii.ServerStatusCodes;
import http.project.networks.ii.Teacher;
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
            Gson gson = new Gson();
            TeachersClass deletedTeachers = gson.fromJson(body.getStringContent(), TeachersClass.class);
            for (Teacher teacher : deletedTeachers.getTeachers()) {
                boolean removed = teachers.removeTeacher(teacher.getName());
                if (!removed) {
                    // Teacher with the given ID not found
                    return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "Teacher with ID " + teacher.getId() + " not found"));
                }
            }

            return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "Teachers deleted successfully"));
        }
        return new Response(ServerStatusCodes.BAD_REQUEST_400.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.INVALID_REQUEST_BODY));
    }
}
