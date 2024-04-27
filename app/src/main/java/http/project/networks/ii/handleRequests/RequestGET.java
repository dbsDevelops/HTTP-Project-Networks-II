package http.project.networks.ii.handleRequests;

import http.project.networks.ii.HTTPUtils;
import http.project.networks.ii.HttpBodyType;
import http.project.networks.ii.HttpRequestBody;
import http.project.networks.ii.Response;
import http.project.networks.ii.ServerStatusCodes;
import http.project.networks.ii.Teacher;
import http.project.networks.ii.TeachersClass;

import java.util.List;

public class RequestGET implements RequestCommand {
    private final String path;
    private final TeachersClass teachers;

    public RequestGET(String path, TeachersClass teachers) {
        this.path = path;
        this.teachers = teachers;
    }

    @Override
    public Response execute() {
        if (!path.equals(HTTPUtils.TEACHERS_PATH)) {
            return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
        }
        return processRequest();
    }

    private Response processRequest() {
        List<Teacher> teacherList = teachers.getTeachers();
        String responseBody = convertTeacherListToJson(teacherList);
        return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.JSON, responseBody));
    }

    private String convertTeacherListToJson(List<Teacher> teacherList) {
        StringBuilder jsonBuilder = new StringBuilder("[");
        for (Teacher teacher : teacherList) {
            jsonBuilder.append("{")
                    .append("\"name\": \"").append(teacher.getName()).append("\",")
                    .append("\"passRate\": ").append(teacher.getPassRate())
                    .append("},");
        }
        if (!teacherList.isEmpty()) {
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1); // Remove the last comma
        }
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }
}
