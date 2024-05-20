package http.project.networks.ii.handle_requests;

import http.project.networks.ii.api.teachers_api.Project;
import http.project.networks.ii.api.teachers_api.Teacher;
import http.project.networks.ii.api.teachers_api.TeachersClass;
import http.project.networks.ii.responses.Response;
import http.project.networks.ii.server.ServerStatusCodes;
import http.project.networks.ii.utils.HTTPUtils;
import http.project.networks.ii.utils.HttpBodyType;
import http.project.networks.ii.utils.HttpBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The {@code RequestConditionalGet} class handles HTTP GET requests with support for conditional
 * headers such as "If-Modified-Since". It implements the {@link RequestCommand} interface.
 */
public class RequestConditionalGet implements RequestCommand {
    private final String path;
    private final TeachersClass teachers;
    private final List<String> headers;

    /**
     * Constructs a {@code RequestConditionalGet} instance.
     *
     * @param path     the path of the requested resource.
     * @param teachers an instance of {@link TeachersClass} containing the teacher data.
     * @param headers  a list of HTTP headers included in the request.
     */
    public RequestConditionalGet(String path, TeachersClass teachers, List<String> headers) {
        this.path = path;
        this.teachers = teachers;
        this.headers = headers;
    }

    /**
     * Executes the conditional GET request. If the "If-Modified-Since" header is present and
     * the resource has not been modified since the specified date, a 304 Not Modified response
     * is returned. Otherwise, the resource is fetched and returned.
     *
     * @return a {@link Response} object representing the result of the request processing.
     */
    @Override
    public Response execute() {
        if (path != null && !path.isEmpty()) {

            // Check conditional headers
            String ifModifiedSinceHeader = findHeader("If-Modified-Since");

            if (ifModifiedSinceHeader != null) {
                // Handle If-Modified-Since header
                System.out.println("\n\nIf-Modified-Since header found: " + ifModifiedSinceHeader + "\n\n");
                LocalDateTime ifModifiedSinceDate = LocalDateTime.parse(ifModifiedSinceHeader.toString(), DateTimeFormatter.RFC_1123_DATE_TIME);
                LocalDateTime lastModifiedDate = getLastModifiedDateOfResource(path);
                if (lastModifiedDate != null && lastModifiedDate.isAfter(ifModifiedSinceDate)) {
                    // Resource has been modified since the specified date
                    
                    if(path.startsWith(HTTPUtils.TEACHERS_PATH)){
                        return new RequestGET(path, teachers).execute();
                    }
                    
                } else {
                    // Resource has not been modified since the specified date
                    return new Response(ServerStatusCodes.NOT_MODIFIED_304.getStatusString(), new HttpBody(HttpBodyType.RAW, "Resource has not been modified"));
                }
            }

            // If no conditional headers or conditions are met, return the resource
            if(path.startsWith(HTTPUtils.TEACHERS_PATH)){
                return new RequestGET(path, teachers).execute();
            }

        }
        return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND));
    }    

    /**
     * Retrieves the last modified date of the resource specified by the given path.
     *
     * @param path the path of the requested resource.
     * @return a {@link LocalDateTime} object representing the last modified date of the resource, or null if the date cannot be determined.
     */
    private LocalDateTime getLastModifiedDateOfResource(String path) {
        // Read the path and get the last modified date of the resource

        if (path.startsWith(HTTPUtils.TEACHERS_PATH)) {
            return teachersGetLastModifiedDate(path);
        }

        return null;
    }

    /**
     * Finds the value of a specific header from the list of headers.
     *
     * @param headerName the name of the header to find.
     * @return the value of the header, or null if the header is not found.
     */
    private String findHeader(String headerName) {
        for (String header : headers) {
            if (header.startsWith(headerName + ":")) {
                return header.substring(headerName.length() + 2).trim();
            }
        }
        return null;
    }

    /**
     * Retrieves the last modified date of a resource in the teachers API based on the given path.
     *
     * @param path the path of the requested resource.
     * @return a {@link LocalDateTime} object representing the last modified date of the resource, or null if the date cannot be determined.
     */
    private LocalDateTime teachersGetLastModifiedDate(String path) {
        if (path.equals(HTTPUtils.TEACHERS_PATH)) {
            return teachers.getLastModified();
        }

        // See if the path is a subpath of teachers
        if (path.startsWith(HTTPUtils.TEACHERS_PATH)) {
            String[] pathParts = path.split("/");
            if (pathParts.length == 3) {
                return teachers.getLastModified();
            }
            if (pathParts.length == 4) {
                if (pathParts[2].equals("teacher")) {
                    Teacher teacher = teachers.getTeacher(pathParts[3]);
                    if (teacher != null) {
                        return teacher.getLastModified();
                    } else {
                        return null;
                    }
                } else if (pathParts[2].equals("project")) {
                    Project project = teachers.getProject(pathParts[3]);
                    if (project != null) {
                        return project.getLastModified();
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        }

        return null;
    }
}
