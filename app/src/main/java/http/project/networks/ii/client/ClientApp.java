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

        app.testGET("https://localhost/teachers/project");

        app.testPOST("https://localhost/teachers", gsonTeacher);

        teacher.setPassRate(3.3f);

        gsonTeacher = gson.toJson(teachers);

        app.testPUT("https://localhost/teachers", gsonTeacher);

        app.testDELETE("https://localhost/teachers/teacher/Teacher_1");

        String Url = "https://localhost/teachers/project";

        app.testConditionalGET(Url, "Sat, 3 Jun 2023 11:05:30 GMT");

        app.testConditionalGET(Url, "Sat, 3 Jun 2028 11:05:30 GMT");


        app.testGET("https://localhost/teachers/");

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

            client = new GreetClient(cachedData);
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

            client = new GreetClient();
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

            client = new GreetClient();
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

            client = new GreetClient();
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

            client = new GreetClient();
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

            client = new GreetClient(cachedData);
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