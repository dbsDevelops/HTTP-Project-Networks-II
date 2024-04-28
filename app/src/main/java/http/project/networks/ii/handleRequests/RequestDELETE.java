package http.project.networks.ii.handleRequests;

import com.google.gson.Gson;

import http.project.networks.ii.HTTPUtils;
import http.project.networks.ii.HttpBodyType;
import http.project.networks.ii.HttpRequestBody;
import http.project.networks.ii.Response;
import http.project.networks.ii.ServerStatusCodes;
import http.project.networks.ii.API.Project;
import http.project.networks.ii.API.Teacher;
import http.project.networks.ii.API.TeachersClass;

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
            TeachersClass deletedData = gson.fromJson(body.getStringContent(), TeachersClass.class);
            boolean removedData = false;
            for (Teacher teacher : deletedData.getTeachers()) {
                boolean removed = teachers.removeTeacher(teacher.getName());
                if (removed) {
                    // Teacher with the given ID not found
                    removedData = true;
                }
            }
            for (Project project : deletedData.getProjects()) {
                boolean removed = teachers.removeProject(project.getName());
                if (removed) {
                    // Project with the given ID not found
                    removedData = true;
                }
            }
            if (!removedData) {
                return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "Teachers not found"));
            }

            
            return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "Teachers deleted successfully"));
        }
        return new Response(ServerStatusCodes.BAD_REQUEST_400.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.INVALID_REQUEST_BODY));
    }
}
