package http.project.networks.ii.handleRequests;

import com.google.gson.Gson;

import http.project.networks.ii.HTTPUtils;
import http.project.networks.ii.HttpBodyType;
import http.project.networks.ii.HttpRequestBody;
import http.project.networks.ii.Response;
import http.project.networks.ii.ServerStatusCodes;
import http.project.networks.ii.Teacher;
import http.project.networks.ii.TeachersClass;

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
            TeachersClass updatedTeachers = gson.fromJson(body.getStringContent(), TeachersClass.class);
            for (Teacher updatedTeacher : updatedTeachers.getTeachers()) {
                // Find and update existing teacher, or add new teacher if not found
                boolean found = false;
                for (Teacher existingTeacher : teachers.getTeachers()) {
                    if (existingTeacher.getId() == updatedTeacher.getId()) {
                        existingTeacher.setName(updatedTeacher.getName());
                        existingTeacher.setPassRate(updatedTeacher.getPassRate());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    teachers.addTeacher(updatedTeacher);
                }
            }
            return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "Teachers updated successfully"));
        }
        return new Response(ServerStatusCodes.BAD_REQUEST_400.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.INVALID_REQUEST_BODY));
    }
}
