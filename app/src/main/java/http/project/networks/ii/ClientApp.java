/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package http.project.networks.ii;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

public class ClientApp {
    
    public static void main(String[] args) {

        try {
            Verbs method = null;                                                           //Method to send the request
            URL url = null;                     //URL to send the request
            String protocolVersion = null;                                                 //Protocol version  
            RequestHeaders headers = null;                                          //Headers
            HttpBodyType bodyType = null;                                            //Body type
            String bodyContent = null;  
            GreetClient client = null;
            Request request = null;                                //Body content

            method = Verbs.POST;                                                           //Method to send the request
            url = new URL("http://localhost/teachers");                     //URL to send the request
            protocolVersion = "HTTP/1.1";                                                 //Protocol version
            headers = new RequestHeaders(url);                                          //Headers
            bodyType = HttpBodyType.JSON;                                            //Body type

            //Body content
            TeachersClass teachers = new TeachersClass();
            teachers.addTeacher(new Teacher("Teacher 1", 0));
            Gson gson = new Gson();
            bodyContent = gson.toJson(teachers);                                      //Body content



            client = new GreetClient(HTTPUtils.HTTP_PORT);
            //Send the request
            request = new Request(method, url, protocolVersion, headers, bodyType, bodyContent);
            System.out.println(request.toString());
            client.sendRequest(url, request);


            method = Verbs.GET;                                                           //Method to send the request
            url = new URL("http://localhost/teachers");                     //URL to send the request
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
    
}
