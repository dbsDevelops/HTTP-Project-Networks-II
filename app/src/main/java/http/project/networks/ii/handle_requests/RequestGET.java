package http.project.networks.ii.handle_requests;

import http.project.networks.ii.api.teachers_api.Project;
import http.project.networks.ii.api.teachers_api.Teacher;
import http.project.networks.ii.api.teachers_api.TeachersClass;
import http.project.networks.ii.responses.Response;
import http.project.networks.ii.server.ServerStatusCodes;
import http.project.networks.ii.utils.HTTPUtils;
import http.project.networks.ii.utils.HttpBodyType;
import http.project.networks.ii.utils.HttpBody;

import java.util.List;

import com.google.gson.Gson;

/**
 * The {@code RequestGET} class handles HTTP GET requests for retrieving resources.
 * It implements the {@link RequestCommand} interface.
 */
public class RequestGET implements RequestCommand {
    private final String path;
    private final TeachersClass teachers;
    private final Gson gson = new Gson();

    /**
     * Constructs a {@code RequestGET} instance.
     *
     * @param path     the path of the requested resource.
     * @param teachers an instance of {@link TeachersClass} containing the teacher data.
     */
    public RequestGET(String path, TeachersClass teachers) {
        this.path = path;
        this.teachers = teachers;
    }

    /**
     * Executes the GET request by processing the specified path and retrieving the resource.
     *
     * @return a {@link Response} object representing the result of the request processing.
     */
    @Override
    public Response execute() {
        if (path.startsWith(HTTPUtils.TEACHERS_PATH)) {
            // See if the path is a subpath of teachers
            if (path.equals(HTTPUtils.TEACHERS_PATH)) {
                return handleTeacher();
            } else {
                return handleTeacherSubpath();
            }
        }

        return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
    }

    /**
     * Handles the GET request for retrieving teacher and project data.
     *
     * @return a {@link Response} object containing teacher and project data.
     */
    private Response handleTeacher() {
        List<Teacher> teacherList = teachers.getTeachers();
        List<Project> projectList = teachers.getProjects();
        TeachersClass teachersClass = new TeachersClass();
        teachersClass.setTeachers(teacherList);
        teachersClass.setProjects(projectList);
        String responseBody = gson.toJson(teachersClass);
        return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpBody(HttpBodyType.JSON, responseBody));
    }

    /**
     * Handles the GET request for retrieving data from teacher subpaths.
     *
     * @return a {@link Response} object containing data from the specified subpath.
     */
    private Response handleTeacherSubpath() {
        String[] pathParts = path.split("/");
        if (pathParts.length == 3) {
            if (pathParts[2].equals("teacher")) {
                List<Teacher> teacherList = teachers.getTeachers();
                String responseBody = gson.toJson(teacherList);
                return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpBody(HttpBodyType.JSON, responseBody));
            } else if (pathParts[2].equals("project")) {
                List<Project> projectList = teachers.getProjects();
                String responseBody = gson.toJson(projectList);
                return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpBody(HttpBodyType.JSON, responseBody));
            } else {
                return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
            }
        }
        if (pathParts.length == 4) {
            if (pathParts[2].equals("teacher")) {
                Teacher teacher = teachers.getTeacher(pathParts[3]);
                if (teacher != null) {
                    String responseBody = gson.toJson(teacher);
                    return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpBody(HttpBodyType.JSON, responseBody));
                } else {
                    return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
                }
            } else if (pathParts[2].equals("project")) {
                Project project = teachers.getProject(pathParts[3]);
                if (project != null) {
                    String responseBody = gson.toJson(project);
                    return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpBody(HttpBodyType.JSON, responseBody));
                } else {
                    return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
                }
            } else {
                return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
            }
        }
        return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
    }
}
