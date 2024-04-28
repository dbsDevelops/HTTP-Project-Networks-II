package http.project.networks.ii;

import http.project.networks.ii.API.Project;
import http.project.networks.ii.API.Teacher;
import http.project.networks.ii.API.TeachersClass;
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
        Teacher[] teachers = {
                new Teacher("Teacher 1", 0.5f, null),
                new Teacher("Teacher 2", 0.6f, null),
                new Teacher("Teacher 3", 0.7f, null),
                new Teacher("Teacher 4", 0.8f, null),
                new Teacher("Teacher 5", 0.9f, null)
        };

        Project[] projects = {
                new Project("Project 1", "Description 1", null, "Student 1", "A", "Completed"),
                new Project("Project 2", "Description 2", null, "Student 2", "B", "In Progress"),
                new Project("Project 3", "Description 3", null, "Student 3", "C", "In Progress"),
                new Project("Project 4", "Description 4", null, "Student 4", "D", "In Progress"),
                new Project("Project 5", "Description 5", null, "Student 5", "E", "In Progress")
        };

        //add the projects to each teacher
        for (int i = 0; i < teachers.length; i++) {
            teachers[i].setProject(projects[i]);
            projects[i].setTeacher(teachers[i].getName());
        }


        for (Teacher teacher : teachers) {
            this.teachers.addTeacher(teacher);
        }

        for (Project project : projects) {
            this.teachers.addProject(project);
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
