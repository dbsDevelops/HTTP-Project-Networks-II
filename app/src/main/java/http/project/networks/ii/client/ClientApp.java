/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package http.project.networks.ii.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import http.project.networks.ii.api.teachers_api.Project;
import http.project.networks.ii.api.teachers_api.Teacher;
import http.project.networks.ii.api.teachers_api.TeachersClass;
import http.project.networks.ii.headers.RequestHeaders;
import http.project.networks.ii.requests.Request;
import http.project.networks.ii.utils.HTTPUtils;
import http.project.networks.ii.utils.HttpBodyType;
import http.project.networks.ii.utils.Verbs;

public class ClientApp {
    CachedData cachedData = new CachedData();
    public static void main(String[] args) {
        ClientApp app = new ClientApp();
        //app.testGET("http://localhost/teachers/");


        // Test Teacher Class
        TeachersClass teachers = new TeachersClass();
        Project project = new Project("Project_6", "Description_1", "Teacher_6", "Student_1", "A", "Completed");
        Teacher teacher = new Teacher("Teacher_6", 0.9f, project);
        teachers.addTeacher(teacher);
        teachers.addProject(project);
        Gson gson = new Gson();

        String gsonTeacher = gson.toJson(teachers);





        // app.testGET("http://localhost/teachers/teacher/Teacher_1");

        // app.testPOST("http://localhost/teachers", gsonTeacher);

        // app.testPUT("http://localhost/teachers/teacher/Teacher_1", "{\"name\":\"Teacher_1\",\"rating\":0.9,\"project\":{\"name\":\"Project_1\",\"description\":\"Description_1\",\"teacher\":\"Teacher_1\",\"student\":\"Student_1\",\"grade\":\"A\",\"status\":\"Completed\"}}");

        // app.testDELETE("http://localhost/teachers/teacher/Teacher_1");

        String Url = "http://localhost/teachers/project";

        app.testConditionalGET(Url, "Sat, 3 Jun 2023 11:05:30 GMT");

        app.testConditionalGET(Url, "Sat, 3 Jun 2028 11:05:30 GMT");


        // app.testGET("http://localhost/teachers/");

    }


    void testGET(String urlPath) {
        try {
            Verbs method = null;                                                           //Method to send the request
            URL url = null;                     //URL to send the request
            String protocolVersion = null;                                                 //Protocol version  
            RequestHeaders headers = null;                                          //Headers
            HttpBodyType bodyType = null;                                            //Body type
            String bodyContent = null;  
            GreetClient client = null;
            Request request = null;                                //Body content

            method = Verbs.GET;                                                           //Method to send the request
            url = new URL(urlPath);                     //URL to send the request
            protocolVersion = "HTTP/1.1";                                                 //Protocol version
            headers = new RequestHeaders(url);                                          //Headers
            bodyType = HttpBodyType.RAW;                                            //Body type
            bodyContent = "";                                      //Body content

            client = new GreetClient(HTTPUtils.HTTP_PORT, cachedData);
            //Send the request
            request = new Request(method, url, protocolVersion, headers, bodyType, bodyContent);
            System.out.println(request.toString());
            client.sendRequest(url, request);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void testPOST(String urlPath, String bodyContent) {
        try {
            Verbs method = null;                                                           //Method to send the request
            URL url = null;                     //URL to send the request
            String protocolVersion = null;                                                 //Protocol version  
            RequestHeaders headers = null;                                          //Headers
            HttpBodyType bodyType = null;                                            //Body type
            GreetClient client = null;
            Request request = null;                                //Body content

            method = Verbs.POST;                                                           //Method to send the request
            url = new URL(urlPath);                     //URL to send the request
            protocolVersion = "HTTP/1.1";                                                 //Protocol version
            headers = new RequestHeaders(url);                                          //Headers
            bodyType = HttpBodyType.JSON;                                            //Body type

            client = new GreetClient(HTTPUtils.HTTP_PORT);
            //Send the request
            request = new Request(method, url, protocolVersion, headers, bodyType, bodyContent);
            System.out.println(request.toString());
            client.sendRequest(url, request);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void testPUT(String urlPath, String bodyContent) {
        try {
            Verbs method = null;                                                           //Method to send the request
            URL url = null;                     //URL to send the request
            String protocolVersion = null;                                                 //Protocol version  
            RequestHeaders headers = null;                                          //Headers
            HttpBodyType bodyType = null;                                            //Body type
            GreetClient client = null;
            Request request = null;                                //Body content

            method = Verbs.PUT;                                                           //Method to send the request
            url = new URL(urlPath);                     //URL to send the request
            protocolVersion = "HTTP/1.1";                                                 //Protocol version
            headers = new RequestHeaders(url);                                          //Headers
            bodyType = HttpBodyType.JSON;                                            //Body type

            client = new GreetClient(HTTPUtils.HTTP_PORT);
            //Send the request
            request = new Request(method, url, protocolVersion, headers, bodyType, bodyContent);
            System.out.println(request.toString());
            client.sendRequest(url, request);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void testHEAD(String urlPath) {
        try {
            Verbs method = null;                                                           //Method to send the request
            URL url = null;                     //URL to send the request
            String protocolVersion = null;                                                 //Protocol version  
            RequestHeaders headers = null;                                          //Headers
            HttpBodyType bodyType = null;                                            //Body type
            String bodyContent = null;  
            GreetClient client = null;
            Request request = null;                                //Body content

            method = Verbs.HEAD;                                                           //Method to send the request
            url = new URL(urlPath);                     //URL to send the request
            protocolVersion = "HTTP/1.1";                                                 //Protocol version
            headers = new RequestHeaders(url);                                          //Headers
            bodyType = HttpBodyType.RAW;                                            //Body type
            bodyContent = "";                                      //Body content

            client = new GreetClient(HTTPUtils.HTTP_PORT);
            //Send the request
            request = new Request(method, url, protocolVersion, headers, bodyType, bodyContent);
            System.out.println(request.toString());
            client.sendRequest(url, request);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void testDELETE(String urlPath) {
        try {
            Verbs method = null;                                                           //Method to send the request
            URL url = null;                     //URL to send the request
            String protocolVersion = null;                                                 //Protocol version  
            RequestHeaders headers = null;                                          //Headers
            HttpBodyType bodyType = null;                                            //Body type
            String bodyContent = null;
            GreetClient client = null;
            Request request = null;                                //Body content

            method = Verbs.DELETE;                                                           //Method to send the request
            url = new URL(urlPath);                     //URL to send the request
            protocolVersion = "HTTP/1.1";                                                 //Protocol version
            headers = new RequestHeaders(url);                                          //Headers
            bodyType = HttpBodyType.JSON;                                            //Body type
            bodyContent = "";                                      //Body content

            client = new GreetClient(HTTPUtils.HTTP_PORT);
            //Send the request
            request = new Request(method, url, protocolVersion, headers, bodyType, bodyContent);
            System.out.println(request.toString());
            client.sendRequest(url, request);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void testConditionalGET(String urlPath, String timeHeaderValue) {
        try {
            Verbs method = null;                                                           //Method to send the request
            URL url = null;                     //URL to send the request
            String protocolVersion = null;                                                 //Protocol version  
            RequestHeaders headers = null;                                          //Headers
            HttpBodyType bodyType = null;                                            //Body type
            String bodyContent = null;  
            GreetClient client = null;
            Request request = null;                                //Body content

            method = Verbs.GET;                                                           //Method to send the request
            url = new URL(urlPath);                     //URL to send the request
            protocolVersion = "HTTP/1.1";                                                 //Protocol version
            headers = new RequestHeaders(url);                                          //Headers
            headers.addHeaderToHeaders("If-Modified-Since", timeHeaderValue);         //Add the header to the headers
            bodyType = HttpBodyType.RAW;                                            //Body type
            bodyContent = "";                                      //Body content

            client = new GreetClient(HTTPUtils.HTTP_PORT, cachedData);
            //Send the request
            request = new Request(method, url, protocolVersion, headers, bodyType, bodyContent);
            System.out.println(request.toString());
            client.sendRequest(url, request);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}