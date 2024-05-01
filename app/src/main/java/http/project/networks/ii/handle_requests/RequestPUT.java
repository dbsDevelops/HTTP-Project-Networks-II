package http.project.networks.ii.handle_requests;

import com.google.gson.Gson;

import http.project.networks.ii.Response;
import http.project.networks.ii.api.teachers_api.Project;
import http.project.networks.ii.api.teachers_api.Teacher;
import http.project.networks.ii.api.teachers_api.TeachersClass;
import http.project.networks.ii.server.ServerStatusCodes;
import http.project.networks.ii.utils.HTTPUtils;
import http.project.networks.ii.utils.HttpBodyType;
import http.project.networks.ii.utils.HttpRequestBody;

public class RequestPUT implements RequestCommand {
    private final String path;
    private final TeachersClass teachers;
    private final HttpRequestBody body;

    public RequestPUT(String path, TeachersClass teachers, HttpRequestBody body) {
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
            TeachersClass updatedData = gson.fromJson(body.getStringContent(), TeachersClass.class);
            for (Teacher teacher : updatedData.getTeachers()) {
                boolean updated = teachers.updateTeacher(teacher);
                if (!updated) {
                    // Teacher with the given ID not found
                    return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "Teacher with ID " + teacher.getId() + " not found"));
                }
            }
            for (Project project : updatedData.getProjects()) {
                boolean updated = teachers.updateProject(project);
                if (!updated) {
                    // Project with the given ID not found
                    return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "Project with ID " + project.getId() + " not found"));
                }
            }

            return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "Data updated successfully"));
        }
        return new Response(ServerStatusCodes.BAD_REQUEST_400.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.INVALID_REQUEST_BODY));
    }
}
