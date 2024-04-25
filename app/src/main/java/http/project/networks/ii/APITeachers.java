package http.project.networks.ii;

import com.google.gson.Gson;

public class APITeachers {

    private TeachersClass teachers;

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
        switch (request.method) {
            case HEAD:
                return handleHeadRequest();
            case GET:
                return handleGetRequest(path);
            case POST:
                return handlePostRequest(path, request.body);
            case PUT:
                return handlePutRequest(path, request.body);
            case DELETE:
                return handleDeleteRequest(path, request.body);
            default:
                return methodNotAllowedResponse();
        }
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

    private Response handleHeadRequest() {
        return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.ESTIMATED_RESPONSE_SIZE + teachers.toString().getBytes().length));
    }

    private Response handleGetRequest(String path) {
        if (path.equals(HTTPUtils.TEACHERS_PATH)) {
            return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, teachers.toString()));
        } else {
            return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
        }
    }

    private Response handlePostRequest(String path, HttpRequestBody body) {
        if (!path.equals(HTTPUtils.TEACHERS_PATH)) {
            return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
        }
        return processBody(body, Verbs.POST);
    }

    private Response handlePutRequest(String path, HttpRequestBody body) {
        if (!path.equals(HTTPUtils.TEACHERS_PATH)) {
            return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
        }
        return processBody(body, Verbs.PUT); 
    }

    private Response handleDeleteRequest(String path, HttpRequestBody body) {
        if (!path.equals(HTTPUtils.TEACHERS_PATH)) {
            return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
        }
        return processBody(body, Verbs.DELETE);
    }

    // false indica actualizar y true indica agregar
    private Response processBody(HttpRequestBody body, Verbs verb) {

        if (body.getType() == HttpBodyType.JSON){
            Gson gson = new Gson();

            TeachersClass teachers = gson.fromJson(body.getStringContent(), TeachersClass.class);

            if (verb == Verbs.POST) {
                for (Teacher teacher : teachers.getTeachers()) {
                    this.teachers.addTeacher(teacher);
                }
                return new Response(ServerStatusCodes.CREATED_201.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_CREATED));
            } else if (verb == Verbs.PUT) {
                for (Teacher teacher : teachers.getTeachers()) {
                    this.teachers.updateTeacher(teacher);
                }
                return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_UPDATED));
            } else if (verb == Verbs.DELETE){
                for (Teacher teacher : teachers.getTeachers()) {
                    this.teachers.removeTeacher(teacher.getName());
                }
                return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_DELETED));
            }



        }

        return new Response(ServerStatusCodes.BAD_REQUEST_400.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.INVALID_REQUEST_BODY));
    }

    private Response methodNotAllowedResponse() {
        return new Response(ServerStatusCodes.METHOD_NOT_ALLOWED_405.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.METHOD_NOT_ALLOWED));
    }
}
