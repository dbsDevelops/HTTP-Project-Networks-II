package http.project.networks.ii.handle_requests;

import http.project.networks.ii.api.teachers_api.Project;
import http.project.networks.ii.api.teachers_api.Teacher;
import http.project.networks.ii.api.teachers_api.TeachersClass;
import http.project.networks.ii.responses.Response;
import http.project.networks.ii.server.ServerStatusCodes;
import http.project.networks.ii.utils.HTTPUtils;
import http.project.networks.ii.utils.HttpBodyType;
import http.project.networks.ii.utils.HttpRequestBody;

import java.util.List;

import com.google.gson.Gson;

public class RequestGET implements RequestCommand {
    private final String path;
    private final TeachersClass teachers;

    public RequestGET(String path, TeachersClass teachers) {
        this.path = path;
        this.teachers = teachers;
    }
    Gson gson = new Gson();
    @Override
    public Response execute() {
        if (!path.equals(HTTPUtils.TEACHERS_PATH)) {
            
            //see if the path is a subpath of teachers
            if (path.startsWith(HTTPUtils.TEACHERS_PATH)) {
                String[] pathParts = path.split("/");
                if (pathParts.length == 3) {
                    if (pathParts[2].equals("teacher")) {
                        List<Teacher> teacherList = teachers.getTeachers();
                        String responseBody = "Body:\n" + gson.toJson(teacherList);
                        return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.JSON, responseBody));
                    }
                    else if (pathParts[2].equals("project")) {
                        List<Project> projectList = teachers.getProjects();
                        String responseBody = "Body:\n" + gson.toJson(projectList);
                        return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.JSON, responseBody));
                    }
                    else {
                        return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
                    }
                }
                if (pathParts.length == 4) {
                    if (pathParts[2].equals("teacher")) {
                        Teacher teacher = teachers.getTeacher(pathParts[3]);
                        if (teacher != null) {
                            String responseBody = "Body:\n" + gson.toJson(teacher);
                            return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.JSON, responseBody));
                        }
                        else {
                            return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
                        }
                    }
                    else if (pathParts[2].equals("project")) {
                        Project project = teachers.getProject(pathParts[3]);
                        if (project != null) {
                            String responseBody = "Body:\n" + gson.toJson(project);
                            return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.JSON, responseBody));
                        }
                        else {
                            return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
                        }
                    }
                    else {
                        return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
                    }
                }
            }

     
            return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
        }
        return processRequest();
    }

    private Response processRequest() {
        List<Teacher> teacherList = teachers.getTeachers();
        List<Project> projectList = teachers.getProjects();
        TeachersClass teachersClass = new TeachersClass();
        teachersClass.setTeachers(teacherList);
        teachersClass.setProjects(projectList);
        String responseBody = "Body:\n" + gson.toJson(teachersClass);
        return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.JSON, responseBody));
    }
}
