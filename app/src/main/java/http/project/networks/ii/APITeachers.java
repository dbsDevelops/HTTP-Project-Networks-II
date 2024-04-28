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
                //conditional GET
                for (String header : request.headers.myHeaders) {
                    if (header.contains("If-Modified-Since")) {
                        command = new RequestConditionalGet(path, teachers, request.headers.myHeaders);
                        return command.execute();
                    }
                }

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
