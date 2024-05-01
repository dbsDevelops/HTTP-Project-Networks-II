package http.project.networks.ii.handleRequests;

import http.project.networks.ii.HTTPUtils;
import http.project.networks.ii.HttpBodyType;
import http.project.networks.ii.HttpRequestBody;
import http.project.networks.ii.Response;
import http.project.networks.ii.api.teachers_api.Project;
import http.project.networks.ii.api.teachers_api.Teacher;
import http.project.networks.ii.api.teachers_api.TeachersClass;
import http.project.networks.ii.server.ServerStatusCodes;

import java.util.List;

public class RequestGET implements RequestCommand {
    private final String path;
    private final TeachersClass teachers;

    public RequestGET(String path, TeachersClass teachers) {
        this.path = path;
        this.teachers = teachers;
    }

    @Override
    public Response execute() {
        if (!path.equals(HTTPUtils.TEACHERS_PATH)) {

            //see if the path is a subpath of teachers
            if (path.startsWith(HTTPUtils.TEACHERS_PATH)) {
                String[] pathParts = path.split("/");
                if (pathParts.length == 3) {
                    if (pathParts[2].equals("teacher")) {
                        List<Teacher> teacherList = teachers.getTeachers();
                        String responseBody = convertListToJson(teacherList);
                        return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.JSON, responseBody));
                    }
                    else if (pathParts[2].equals("project")) {
                        List<Project> projectList = teachers.getProjects();
                        String responseBody = convertListToJson(projectList);
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
                            String responseBody = teacher.toString();
                            return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.JSON, responseBody));
                        }
                        else {
                            return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
                        }
                    }
                    else if (pathParts[2].equals("project")) {
                        Project project = teachers.getProject(pathParts[3]);
                        if (project != null) {
                            String responseBody = project.toString();
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
        String responseBody = convertDataToJson(teacherList, projectList);
        return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.JSON, responseBody));
    }

    private String convertDataToJson(List<Teacher> teacherList, List<Project> projectList) {
        StringBuilder jsonBuilder = new StringBuilder("{").append("\n");
        jsonBuilder.append("\"teachers\": ").append("\n").append(convertListToJson(teacherList)).append("\n");
        jsonBuilder.append("\"projects\": ").append("\n").append(convertListToJson(projectList)).append("\n");
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    private String convertListToJson(List<?> list) {
        StringBuilder jsonBuilder = new StringBuilder("[").append("\n");
        for (Object obj : list) {
            jsonBuilder.append(obj.toString()).append("\n");

        }
        if (!list.isEmpty()) {
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1); // Remove the last comma
        }
        jsonBuilder.append("\n").append("]");
        return jsonBuilder.toString();
    }
}
