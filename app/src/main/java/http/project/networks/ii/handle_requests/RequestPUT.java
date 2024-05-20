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
 * The {@code RequestPUT} class handles HTTP PUT requests for updating existing resources.
 * It implements the {@link RequestCommand} interface.
 */
public class RequestPUT implements RequestCommand {
    private final String path;
    private final TeachersClass teachers;
    private final HttpBody body;

    /**
     * Constructs a {@code RequestPUT} instance.
     *
     * @param path     the path of the resource to be updated.
     * @param teachers an instance of {@link TeachersClass} containing the teacher data.
     * @param body     the HTTP body containing the data for updating the resource.
     */
    public RequestPUT(String path, TeachersClass teachers, HttpBody body) {
        this.path = path;
        this.teachers = teachers;
        this.body = body;
    }

    /**
     * Executes the PUT request by processing the specified path and updating the resource.
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
     * Processes the body of the PUT request for updating teachers and projects.
     *
     * @param body the HTTP body containing the data for updating the resource.
     * @return a {@link Response} object representing the result of the request processing.
     */
    private Response processBodyTeachers(HttpBody body) {
        if (body.getType() == HttpBodyType.JSON) {
            Gson gson = new Gson();
            TeachersClass updatedData = gson.fromJson(body.getStringContent(), TeachersClass.class);
            for (Teacher teacher : updatedData.getTeachers()) {
                boolean updated = teachers.updateTeacher(teacher);
                if (!updated) {
                    // Teacher with the given ID not found
                    return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpBody(HttpBodyType.RAW, "Teacher with ID " + teacher.getId() + " not found"));
                }
            }
            for (Project project : updatedData.getProjects()) {
                boolean updated = teachers.updateProject(project);
                if (!updated) {
                    // Project with the given ID not found
                    return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpBody(HttpBodyType.RAW, "Project with ID " + project.getId() + " not found"));
                }
            }

            return new Response(ServerStatusCodes.OK_200.getStatusString(), new HttpBody(HttpBodyType.RAW, "Data updated successfully"));
        }
        return new Response(ServerStatusCodes.BAD_REQUEST_400.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.INVALID_REQUEST_BODY));
    }
}
