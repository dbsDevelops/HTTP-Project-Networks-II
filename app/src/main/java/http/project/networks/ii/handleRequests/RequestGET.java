package http.project.networks.ii.handleRequests;

import http.project.networks.ii.HTTPUtils;
import http.project.networks.ii.HttpBodyType;
import http.project.networks.ii.HttpRequestBody;
import http.project.networks.ii.Response;
import http.project.networks.ii.ServerStatusCodes;
import http.project.networks.ii.API.Teacher;
import http.project.networks.ii.API.Project;
import http.project.networks.ii.API.TeachersClass;

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
                if (pathParts.length == 4) {
                    if (pathParts[2].equals("teacher")) {
                        String teacherName = pathParts[3];
                        List<Teacher> teacherList = teachers.getTeachers();
                        for (Teacher teacher : teacherList) {
                            if (teacher.getName().equals(teacherName)) {
                                StringBuilder jsonBuilder = new StringBuilder("{").append("\n");
                                jsonBuilder.append("\"teacher\": ").append("\n").append(teacher.toString()).append("\n");
                                jsonBuilder.append("}");
                                return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.JSON, jsonBuilder.toString()));
                            }
                        }
                    } else if (pathParts[2].equals("project")) {
                        String projectName = pathParts[3];
                        List<Project> projectList = teachers.getProjects();
                        for (Project project : projectList) {
                            if (project.getName().equals(projectName)) {
                                StringBuilder jsonBuilder = new StringBuilder("{").append("\n");
                                jsonBuilder.append("\"project\": ").append("\n").append(project.toString()).append("\n");
                                jsonBuilder.append("}");
                                return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpRequestBody(HttpBodyType.JSON, jsonBuilder.toString()));
                            }
                        }
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
