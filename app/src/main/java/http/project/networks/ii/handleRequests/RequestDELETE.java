package http.project.networks.ii.handleRequests;

import java.util.List;

import com.google.gson.Gson;

import http.project.networks.ii.HTTPUtils;
import http.project.networks.ii.HttpBodyType;
import http.project.networks.ii.HttpRequestBody;
import http.project.networks.ii.Response;
import http.project.networks.ii.ServerStatusCodes;
import http.project.networks.ii.API.Project;
import http.project.networks.ii.API.Teacher;
import http.project.networks.ii.API.TeachersClass;

public class RequestDELETE implements RequestCommand {
    private final String path;
    private final TeachersClass teachers;

    public RequestDELETE(String path, TeachersClass teachers) {
        this.path = path;
        this.teachers = teachers;
    }

    @Override
    public Response execute() {
        return processPath();
    }

    private Response processPath() {
        if (!path.equals(HTTPUtils.TEACHERS_PATH)) {

            //see if the path is a subpath of teachers
            if (path.startsWith(HTTPUtils.TEACHERS_PATH)) {
                String[] pathParts = path.split("/");
                if (pathParts.length == 3) {
                    if (pathParts[2].equals("teacher")) {
                        teachers.clearTeachers();
                        return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "All teachers deleted"));
                    }
                    else if (pathParts[2].equals("project")) {
                        teachers.clearProjects();
                        return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "All projects deleted"));
                    }
                    else {
                        return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
                    }
                }
                if (pathParts.length == 4) {
                    if (pathParts[2].equals("teacher")) {
                        Teacher teacher = teachers.getTeacher(pathParts[3]);
                        if (teacher != null) {
                            teachers.removeTeacher(teacher.getName());
                            return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "Teacher deleted"));
                        }
                        else {
                            return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
                        }
                    }
                    else if (pathParts[2].equals("project")) {
                        Project project = teachers.getProject(pathParts[3]);
                        if (project != null) {
                            teachers.removeProject(project.getName());
                            return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "Project deleted"));
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
        else{
            teachers.clear();
            return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "All teachers and projects deleted"));    
        }
    }
}
