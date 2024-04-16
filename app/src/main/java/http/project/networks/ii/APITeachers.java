package http.project.networks.ii;


public class APITeachers {

    TeachersClass teachers;

    public APITeachers(TeachersClass teachers) {
        this.teachers = teachers;
    }

    public APITeachers() {
        this.teachers = new TeachersClass();
    }

    public void teachersMockData() {
        teachers.addTeacher(new Teacher("Teacher 1", 0));
        teachers.addTeacher(new Teacher("Teacher 2", 0));
        teachers.addTeacher(new Teacher("Teacher 3", 0));
        teachers.addTeacher(new Teacher("Teacher 4", 0));
        teachers.addTeacher(new Teacher("Teacher 5", 0));
    }

    public Response readRequest(Request request) {
        String path = extractPath(request.url.getPath());
        switch (request.method) {
            case HEAD:
                return handleHead();
            case GET:
                return handleGet(path);
            case POST:
                return handlePost(path, request.body);
            case PUT:
                return handlePut(path, request.body);
            case DELETE:
                return handleDelete(path, request.body);
            default:
                return new Response(ServerStatusCodes.METHOD_NOT_ALLOWED_405.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.METHOD_NOT_ALLOWED));
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

    private Response handleHead() {
        return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.ESTIMATED_RESPONSE_SIZE + teachers.toString().getBytes().length));
    }

    private Response handleGet(String path) {
        if (path.equals(HTTPUtils.TEACHERS_PATH)) {
            return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, teachers.toString()));
        } else {
            return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
        }
    }

    private Response handlePost(String path, HttpRequestBody body) {
        if (!path.equals(HTTPUtils.TEACHERS_PATH)) {
            return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
        }
        return processBody(body, true);
    }

    private Response handlePut(String path, HttpRequestBody body) {
        if (!path.equals(HTTPUtils.TEACHERS_PATH)) {
            return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
        }
        return processBody(body, false); 
    }

    private Response handleDelete(String path, HttpRequestBody body) {
        if (!path.equals(HTTPUtils.TEACHERS_PATH)) {
            return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
        }
        String content = body.getStringContent();
        String[] contentParts = content.split(HTTPUtils.NEW_LINE_CHARACTER);
        for (String part : contentParts) {
            teachers.removeTeacher(part.trim());
        }
        return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_DELETED));
    }

    // false indica actualizar y true indica agregar
    private Response processBody(HttpRequestBody body, boolean add) {
        if (body == null || body.getType() != HttpBodyType.RAW) {
            return new Response(ServerStatusCodes.BAD_REQUEST_400.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "Invalid request body"));
        }
        String content = body.getStringContent();
        String[] contentParts = content.split(HTTPUtils.NEW_LINE_CHARACTER);
        for (String part : contentParts) {
            String[] teacherParts = part.split(HTTPUtils.SPACE_CHARACTER);
            if (teacherParts.length != 2) {
                return new Response(ServerStatusCodes.BAD_REQUEST_400.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "Invalid teacher format"));
            }
            int avg = Integer.parseInt(teacherParts[1].trim());
            if (add) {
                teachers.addTeacher(new Teacher(teacherParts[0], avg));
            } else {
                teachers.updateTeacher(new Teacher(teacherParts[0], avg));
            }
        }
        return add ? new Response(ServerStatusCodes.CREATED_201.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_CREATED))
                   : new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_UPDATED));
    }
}
