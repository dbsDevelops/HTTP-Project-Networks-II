package http.project.networks.ii.handle_requests;

import http.project.networks.ii.HTTPUtils;
import http.project.networks.ii.HttpBodyType;
import http.project.networks.ii.HttpRequestBody;
import http.project.networks.ii.Response;
import http.project.networks.ii.api.teachers_api.Project;
import http.project.networks.ii.api.teachers_api.Teacher;
import http.project.networks.ii.api.teachers_api.TeachersClass;
import http.project.networks.ii.server.ServerStatusCodes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RequestConditionalGet implements RequestCommand {
    private final String path;
    private final TeachersClass teachers;
    private final List<String> headers;

    public RequestConditionalGet(String path, TeachersClass teachers, List<String> headers) {
        this.path = path;
        this.teachers = teachers;
        this.headers = headers;
    }

    @Override
    public Response execute() {
        if (!path.startsWith(HTTPUtils.TEACHERS_PATH)) {
            return new Response(ServerStatusCodes.NOT_FOUND_404.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, HTTPUtils.RESOURCE_NOT_FOUND + " position 1"));
        }
        
        // Check conditional headers
        String ifModifiedSinceHeader = findHeader("If-Modified-Since");

        if (ifModifiedSinceHeader != null) {
            // Handle If-Modified-Since header
            System.out.println("\n\nIf-Modified-Since header found: " + ifModifiedSinceHeader + "\n\n");
            LocalDateTime ifModifiedSinceDate = LocalDateTime.parse(ifModifiedSinceHeader.toString(), DateTimeFormatter.RFC_1123_DATE_TIME);
            LocalDateTime lastModifiedDate = getLastModifiedDateOfResource(path, ifModifiedSinceDate); // You need to implement this method
            if (lastModifiedDate != null && lastModifiedDate.isAfter(ifModifiedSinceDate)) {
                // Resource has been modified since the specified date
                return new RequestGET(path, teachers).execute();
            } else {
                // Resource has not been modified since the specified date
                return new Response(ServerStatusCodes.NOT_MODIFIED_304.getStatusString(), new HttpRequestBody(HttpBodyType.RAW, "Resource has not been modified"));
            }
        }
        

        // If no conditional headers or conditions are met, return the resource
        return new RequestGET(path, teachers).execute();
    }
    
    private LocalDateTime getLastModifiedDateOfResource(String path, LocalDateTime ifModifiedSinceDate) {

        //read the path and get the last modified date of the resource

        if (!path.equals(HTTPUtils.TEACHERS_PATH)) {

            //see if the path is a subpath of teachers
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
                        }
                        else {
                            return null;
                        }
                    }
                    else if (pathParts[2].equals("project")) {
                        Project project = teachers.getProject(pathParts[3]);
                        if (project != null) {
                            return project.getLastModified();
                        }
                        else {
                            return null;
                        }
                    }
                    else {
                        return null;
                    }
                }
            }

        }


        return null;
    }

    private String findHeader(String headerName) {
        for (String header : headers) {
            if (header.startsWith(headerName + ":")) {
                return header.substring(headerName.length() + 2).trim();
            }
        }
        return null;
    }
}
