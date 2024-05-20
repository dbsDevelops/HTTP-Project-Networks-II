package http.project.networks.ii.api.teachers_api;

import http.project.networks.ii.handle_requests.*;
import http.project.networks.ii.requests.Request;
import http.project.networks.ii.responses.Response;
import http.project.networks.ii.server.ServerStatusCodes;
import http.project.networks.ii.utils.HTTPUtils;
import http.project.networks.ii.utils.HttpBodyType;
import http.project.networks.ii.utils.HttpBody;
import http.project.networks.ii.utils.HttpRequestHeaders;

/**
 * The APITeachers class is responsible for managing teacher and project data,
 * initializing mock data, and handling HTTP requests.
 */
public class APITeachers {

    private final TeachersClass teachers;

    /**
     * Constructs an APITeachers object with a given TeachersClass instance.
     *
     * @param teachers the TeachersClass instance to use
     */
    public APITeachers(TeachersClass teachers) {
        this.teachers = teachers;
    }

    /**
     * Constructs an APITeachers object with a new TeachersClass instance.
     */
    public APITeachers() {
        this.teachers = new TeachersClass();
    }

    /**
     * Initializes mock data for teachers and projects.
     */
    public void initialiseTeachersMockData() {
        Teacher[] teachers = {
                new Teacher("Teacher_1", 0.5f, null),
                new Teacher("Teacher_2", 0.6f, null),
                new Teacher("Teacher_3", 0.7f, null),
                new Teacher("Teacher_4", 0.8f, null),
                new Teacher("Teacher_5", 0.9f, null)
        };

        Project[] projects = {
                new Project("Project_1", "Description_1", null, "Student_1", "A", "Completed"),
                new Project("Project_2", "Description_2", null, "Student_2", "B", "In_Progress"),
                new Project("Project_3", "Description_3", null, "Student_3", "C", "In_Progress"),
                new Project("Project_4", "Description_4", null, "Student_4", "D", "In_Progress"),
                new Project("Project_5", "Description_5", null, "Student_5", "E", "In_Progress")
        };

        // Assign projects to teachers
        for (int i = 0; i < teachers.length; i++) {
            teachers[i].setProject(projects[i]);
            projects[i].setTeacher(teachers[i].getName());
        }

        // Add teachers to the TeachersClass instance
        for (Teacher teacher : teachers) {
            this.teachers.addTeacher(teacher);
        }

        // Add projects to the TeachersClass instance
        for (Project project : projects) {
            this.teachers.addProject(project);
        }
    }

    /**
     * Reads and processes an HTTP request.
     *
     * @param request the HTTP request to read
     * @return the response to the request
     */
    public Response readRequest(Request request) {
        String path = extractPath(request.getUrl().getPath());
        RequestCommand command;

        switch (request.getMethod()) {
            case HEAD:
                command = new RequestHEAD(path);
                break;
            case GET:
                // Handle conditional GET request
                if (request.getRequestHeadersObject().getValue(HttpRequestHeaders.IF_MODIFIED_SINCE) != null) {
                    command = new RequestConditionalGet(path, teachers, request.getRequestHeadersObject().getHeaders());
                    return command.execute();
                }
                command = new RequestGET(path, teachers);
                break;
            case POST:
                command = new RequestPOST(path, teachers, request.getBody());
                break;
            case PUT:
                command = new RequestPUT(path, teachers, request.getBody());
                break;
            case DELETE:
                command = new RequestDELETE(path, teachers);
                break;
            default:
                return methodNotAllowedResponse();
        }

        return command.execute();
    }

    /**
     * Extracts and normalizes the path from a given URL.
     *
     * @param url the URL to extract the path from
     * @return the normalized path
     */
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

    /**
     * Returns a response indicating that the HTTP method is not allowed.
     *
     * @return the response with a 405 Method Not Allowed status
     */
    private Response methodNotAllowedResponse() {
        return new Response(ServerStatusCodes.METHOD_NOT_ALLOWED_405.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.METHOD_NOT_ALLOWED));
    }
}
