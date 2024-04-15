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

    public String readRequest(Request request) {
        Response response = null;
        String url = request.url.getPath();
        String[] urlParts = url.split(HTTPUtils.SLASH_CHARACTER);
        StringBuilder path = new StringBuilder();
        // ...

        for (String part : urlParts) {
            if (part.isEmpty()) {
                continue;
            }
            path.append(HTTPUtils.SLASH_CHARACTER).append(part);
        }
        
        switch (request.method) {
            case HEAD:
                response = new Response(ServerStatusCodes.OK_200.getStatusString(), HTTPUtils.ESTIMATED_RESPONSE_SIZE + teachers.toString().getBytes().length);
                break;
            case GET:
                if (path.toString().equals(HTTPUtils.TEACHERS_PATH)) {
                    response = new Response(ServerStatusCodes.OK_200.getStatusString(), teachers.toString());
                } else {
                    response = new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), HTTPUtils.RESOURCE_NOT_FOUND);
                }
                break;
            case POST:
                if (path.toString().equals(HTTPUtils.TEACHERS_PATH)) {
                    response = new Response(ServerStatusCodes.CREATED_201.getStatusString(), HTTPUtils.RESOURCE_CREATED);
                } else {
                    response = new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), HTTPUtils.RESOURCE_NOT_FOUND);
                }
                break;
            case PUT:
                if (path.toString().equals(HTTPUtils.TEACHERS_PATH)) {
                    response = new Response(ServerStatusCodes.OK_200.getStatusString(), HTTPUtils.RESOURCE_UPDATED);
                } else {
                    response = new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), HTTPUtils.RESOURCE_NOT_FOUND);
                }
                break;
            case DELETE:
                if (path.toString().equals(HTTPUtils.TEACHERS_PATH)) {
                    response = new Response(ServerStatusCodes.OK_200.getStatusString(), HTTPUtils.RESOURCE_DELETED);
                } else {
                    response = new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), HTTPUtils.RESOURCE_NOT_FOUND);
                }
                break;
            default:
                response = new Response(ServerStatusCodes.METHOD_NOT_ALLOWED_405.getStatusString(), HTTPUtils.METHOD_NOT_ALLOWED);
                break;
        }

        return response.toString();

    }
    
}
