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
        String[] urlParts = url.split("/");
        String path = "";
        for (String part : urlParts) {
            if (part.isEmpty()) {
                continue;
            }
            path += "/" + part;
        }
        
        switch (request.method) {
            case HEAD:
                response = new Response(200, "OK", "Estimate response size: " + teachers.toString().getBytes().length);
                break;
            case GET:
                if (path.equals("/teachers")) {
                    response = new Response(200, "OK", teachers.toString());
                } else {
                    response = new Response(404, "Not Found", "Resource not found");
                }
                break;
            case POST:
                if (path.equals("/teachers")) {
                    response = new Response(201, "Created", "Resource created");
                } else {
                    response = new Response(404, "Not Found", "Resource not found");
                }
                break;
            case PUT:
                if (path.equals("/teachers")) {
                    response = new Response(200, "OK", "Resource updated");
                } else {
                    response = new Response(404, "Not Found", "Resource not found");
                }
                break;
            case DELETE:
                if (path.equals("/teachers")) {
                    response = new Response(200, "OK", "Resource deleted");
                } else {
                    response = new Response(404, "Not Found", "Resource not found");
                }
                break;
            default:
                response = new Response(405, "Method Not Allowed", "Method not allowed");
                break;
        }

        return response.toString();

    }
    
}
