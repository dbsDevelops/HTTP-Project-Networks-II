package http.project.networks.ii;

import http.project.networks.ii.handleRequests.*;

public class APITeachers {

    private final TeachersClass teachers;

    public APITeachers(TeachersClass teachers) {
        this.teachers = teachers;
    }

    public APITeachers() {
        this.teachers = new TeachersClass();
    }

    public void initialiseTeachersMockData() {
        String[] teacherNames = {"Teacher 1", "Teacher 2", "Teacher 3", "Teacher 4", "Teacher 5"};
        for (String name : teacherNames) {
            teachers.addTeacher(new Teacher(name, 0));
        }
    }

    public Response readRequest(Request request) {
        String path = extractPath(request.url.getPath());
        RequestCommand command;

        switch (request.method) {
            case HEAD:
                command = new RequestHEAD(path);
                break;
            case GET:
                command = new RequestGET(path, teachers);
                break;
            case POST:
                command = new RequestPOST(path, teachers, request.body);
                break;
            case PUT:
                command = new RequestPUT(path, teachers, request.body);
                break;
            case DELETE:
                command = new RequestDELETE(path, teachers, request.body);
                break;
            default:
                return methodNotAllowedResponse();
        }

        return command.execute();
    }

    private String extractPath(String url) {
        String[] urlParts = url.split(HTTPUtils.SLASH_CHARACTER);
        StringBuilder path = new StringBuilder();
        for (String part : urlParts) {
            if (!part.isEmpty()) {
                path.append(HTTPUtils.SLASH_CHARACTER).append(part);
            }
        }
        return path.toString();
    }

    private Response methodNotAllowedResponse() {
        return new Response(ServerStatusCodes.METHOD_NOT_ALLOWED_405.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.METHOD_NOT_ALLOWED));
    }
}
