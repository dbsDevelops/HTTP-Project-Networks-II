package http.project.networks.ii.handle_requests;

import com.google.gson.Gson;

import http.project.networks.ii.api.teachers_api.Project;
import http.project.networks.ii.api.teachers_api.Teacher;
import http.project.networks.ii.api.teachers_api.TeachersClass;
import http.project.networks.ii.responses.Response;
import http.project.networks.ii.server.ServerStatusCodes;
import http.project.networks.ii.utils.HTTPUtils;
import http.project.networks.ii.utils.HttpBodyType;
import http.project.networks.ii.utils.HttpRequestBody;

public class RequestPOST implements RequestCommand {
    private final String path;
    private final TeachersClass teachers;
    private final HttpRequestBody body;

    public RequestPOST(String path, TeachersClass teachers, HttpRequestBody body) {
        this.path = path;
        this.teachers = teachers;
        this.body = body;
    }

    @Override
    public Response execute() {
        if (path.equals(HTTPUtils.TEACHERS_PATH)) {
            return processBodyTeachers(body);
        }


        return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
    }

    private Response processBodyTeachers(HttpRequestBody body) {
        if (body.getType() == HttpBodyType.JSON) {
            Gson gson = new Gson();
            TeachersClass data = gson.fromJson(body.getStringContent(), TeachersClass.class);
            for (Teacher teacher : data.getTeachers()) {
                this.teachers.addTeacher(teacher);
            }
            for (Project project : data.getProjects()) {
                this.teachers.addProject(project);
            }
            return new Response(ServerStatusCodes.CREATED_201.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_CREATED));
        }
        return new Response(ServerStatusCodes.BAD_REQUEST_400.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.INVALID_REQUEST_BODY));
    }
}
