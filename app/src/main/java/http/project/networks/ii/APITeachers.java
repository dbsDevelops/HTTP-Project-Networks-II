package http.project.networks.ii;

public class APITeachers {

    private static final String SLASH_CHARACTER = "/";
    private static final String TEACHERS_PATH = "/teachers";
    private static final String RESOURCE_NOT_FOUND = "Resource not found";
    private static final String RESOURCE_CREATED = "Resource created";
    private static final String RESOURCE_UPDATED = "Resource updated";
    private static final String RESOURCE_DELETED = "Resource deleted";
    private static final String METHOD_NOT_ALLOWED = "Method not allowed";

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
        String[] urlParts = url.split(SLASH_CHARACTER);
        StringBuilder path = new StringBuilder();
        for (String part : urlParts) {
            if (part.isEmpty()) {
                continue;
            }
            path.append(SLASH_CHARACTER).append(part);
        }
        
        switch (request.method) {
            case HEAD:
                response = new Response(ServerStatusCodes.OK_200.toString(), "Estimate response size: " + teachers.toString().getBytes().length);
                break;
            case GET:
                if (path.toString().equals(TEACHERS_PATH)) {
                    response = new Response(ServerStatusCodes.OK_200.toString(), teachers.toString());
                } else {
                    response = new Response(ServerStatusCodes.NOT_FOUND_404.toString(), RESOURCE_NOT_FOUND);
                }
                break;
            case POST:
                if (path.toString().equals(TEACHERS_PATH)) {
                    response = new Response(ServerStatusCodes.CREATED_201.toString(), RESOURCE_CREATED);
                } else {
                    response = new Response(ServerStatusCodes.NOT_FOUND_404.toString(), RESOURCE_NOT_FOUND);
                }
                break;
            case PUT:
                if (path.toString().equals(TEACHERS_PATH)) {
                    response = new Response(ServerStatusCodes.OK_200.toString(), RESOURCE_UPDATED);
                } else {
                    response = new Response(ServerStatusCodes.NOT_FOUND_404.toString(), RESOURCE_NOT_FOUND);
                }
                break;
            case DELETE:
                if (path.toString().equals(TEACHERS_PATH)) {
                    response = new Response(ServerStatusCodes.OK_200.toString(), RESOURCE_DELETED);
                } else {
                    response = new Response(ServerStatusCodes.NOT_FOUND_404.toString(), RESOURCE_NOT_FOUND);
                }
                break;
            default:
                response = new Response(ServerStatusCodes.METHOD_NOT_ALLOWED_405.toString(), METHOD_NOT_ALLOWED);
                break;
        }

        return response.toString();

    }
    
}
