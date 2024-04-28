/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package http.project.networks.ii;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

import http.project.networks.ii.API.Project;
import http.project.networks.ii.API.Teacher;
import http.project.networks.ii.API.TeachersClass;

public class ClientApp {
    
    public static void main(String[] args) {

        ClientApp app = new ClientApp();
        app.testGET("http://localhost/teachers");

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

            client = GreetClient.getInstance(HTTPUtils.HTTP_PORT);
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

            client = GreetClient.getInstance(HTTPUtils.HTTP_PORT);
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

            client = GreetClient.getInstance(HTTPUtils.HTTP_PORT);
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

            client = GreetClient.getInstance(HTTPUtils.HTTP_PORT);
            //Send the request
            request = new Request(method, url, protocolVersion, headers, bodyType, bodyContent);
            System.out.println(request.toString());
            client.sendRequest(url, request);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void testDELETE(String urlPath, String bodyContent) {
        try {
            Verbs method = null;                                                           //Method to send the request
            URL url = null;                     //URL to send the request
            String protocolVersion = null;                                                 //Protocol version  
            RequestHeaders headers = null;                                          //Headers
            HttpBodyType bodyType = null;                                            //Body type
            GreetClient client = null;
            Request request = null;                                //Body content

            method = Verbs.DELETE;                                                           //Method to send the request
            url = new URL(urlPath);                     //URL to send the request
            protocolVersion = "HTTP/1.1";                                                 //Protocol version
            headers = new RequestHeaders(url);                                          //Headers
            bodyType = HttpBodyType.JSON;                                            //Body type

            client = GreetClient.getInstance(HTTPUtils.HTTP_PORT);
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
