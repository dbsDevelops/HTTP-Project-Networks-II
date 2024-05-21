package http.project.networks.ii.handle_requests;

import com.google.gson.Gson;

import http.project.networks.ii.api.teachers_api.Project;
import http.project.networks.ii.api.teachers_api.Teacher;
import http.project.networks.ii.api.teachers_api.TeachersClass;
import http.project.networks.ii.responses.Response;
import http.project.networks.ii.server.ServerStatusCodes;
import http.project.networks.ii.utils.HTTPUtils;
import http.project.networks.ii.utils.HttpBodyType;
import http.project.networks.ii.utils.HttpBody;

/**
 * The {@code RequestPOST} class handles HTTP POST requests for creating new resources.
 * It implements the {@link RequestCommand} interface.
 */
public class RequestPOST implements RequestCommand {
    private final String path;
    private final TeachersClass teachers;
    private final HttpBody body;

    /**
     * Constructs a {@code RequestPOST} instance.
     *
     * @param path     the path of the resource to be created.
     * @param teachers an instance of {@link TeachersClass} containing the teacher data.
     * @param body     the HTTP body containing the data for creating the resource.
     */
    public RequestPOST(String path, TeachersClass teachers, HttpBody body) {
        this.path = path;
        this.teachers = teachers;
        this.body = body;
    }

    /**
     * Executes the POST request by processing the specified path and creating the resource.
     *
     * @return a {@link Response} object representing the result of the request processing.
     */
    @Override
    public Response execute() {
        if (path.equals(HTTPUtils.TEACHERS_PATH)) {
            return processBodyTeachers(body);
        }

        return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
    }

    /**
     * Processes the body of the POST request for creating teachers and projects.
     *
     * @param body the HTTP body containing the data for creating the resource.
     * @return a {@link Response} object representing the result of the request processing.
     */
    private Response processBodyTeachers(HttpBody body) {
        if (body.getType() == HttpBodyType.JSON) {
            Gson gson = new Gson();

            try {
                TeachersClass data = gson.fromJson(body.getStringContent(), TeachersClass.class);
                if (data == null) {
                    return new Response(ServerStatusCodes.BAD_REQUEST_400.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.INVALID_REQUEST_BODY));
                }

                if (data.getClass() != TeachersClass.class) {
                    return new Response(ServerStatusCodes.BAD_REQUEST_400.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.INVALID_REQUEST_BODY));
                }
                if (data.getTeachers() == null || data.getProjects() == null) {
                    return new Response(ServerStatusCodes.BAD_REQUEST_400.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.INVALID_REQUEST_BODY));
                }

                for (Teacher teacher : data.getTeachers()) {
                    this.teachers.addTeacher(teacher);
                }
                for (Project project : data.getProjects()) {
                    this.teachers.addProject(project);
                }
                return new Response(ServerStatusCodes.CREATED_201.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_CREATED));
            } catch (Exception e) {
                return new Response(ServerStatusCodes.BAD_REQUEST_400.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.INVALID_REQUEST_BODY));
            }
        }
        return new Response(ServerStatusCodes.BAD_REQUEST_400.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.INVALID_REQUEST_BODY));

    }
}
